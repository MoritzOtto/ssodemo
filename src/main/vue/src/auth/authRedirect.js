// Create the main myMSALObj instance
// configuration parameters are located at authConfig.js
import * as msal from "@azure/msal-browser";

let username = "";
let authToken = undefined;
const callBacks = [];

/**
 * Configuration object to be passed to MSAL instance on creation.
 * For a full list of MSAL.js configuration parameters, visit:
 * https://github.com/AzureAD/microsoft-authentication-library-for-js/blob/dev/lib/msal-browser/docs/configuration.md
 */
export const msalConfig = {
  auth: {
    clientId: import.meta.env.VITE_APP_CLIENTID,
    authority: import.meta.env.VITE_APP_AUTHORITY,
    redirectUri: import.meta.env.VITE_APP_REDIRECT_URL,
  },
  cache: {
    cacheLocation: "sessionStorage", // This configures where your cache will be stored
    storeAuthStateInCookie: false, // Set this to "true" if you are having issues on IE11 or Edge
  },
  system: {
    loggerOptions: {
      loggerCallback: (level, message, containsPii) => {
        if (containsPii) {
          return;
        }

        switch (level) {
          case msal.LogLevel.Error:
            console.error(message);
            return;
          case msal.LogLevel.Info:
            console.info(message);
            return;
          case msal.LogLevel.Verbose:
            console.debug(message);
            return;
          case msal.LogLevel.Warning:
            console.warn(message);
            return;
        }
      },
    },
  },
};

/**
 * Scopes you add here will be prompted for user consent during sign-in.
 * By default, MSAL.js will add OIDC scopes (openid, profile, email) to any login request.
 * For more information about OIDC scopes, visit:
 * https://docs.microsoft.com/en-us/azure/active-directory/develop/v2-permissions-and-consent#openid-connect-scopes
 */
export const loginRequest = {
  scopes: [import.meta.env.VITE_APP_API_SCOPE],
};

/**
 * A promise handler needs to be registered for handling the
 * response returned from redirect flow. For more information, visit:
 * https://github.com/AzureAD/microsoft-authentication-library-for-js/blob/dev/lib/msal-browser/docs/acquire-token.md
 */
const myMSALObj = new msal.PublicClientApplication(msalConfig);
myMSALObj
  .handleRedirectPromise()
  .then(handleResponse)
  .catch((error) => {
    console.error(error);
  });

export function registerCallback(callback) {
  callBacks.push(callback);
}

function selectAccount() {
  /**
   * See here for more info on account retrieval:
   * https://github.com/AzureAD/microsoft-authentication-library-for-js/blob/dev/lib/msal-common/docs/Accounts.md
   */

  const currentAccounts = myMSALObj.getAllAccounts();

  if (currentAccounts.length > 1) {
    // Add your account choosing logic here
    console.warn("Multiple accounts detected.");
  } else if (currentAccounts.length === 1) {
    username = currentAccounts[0].username;
  }
}

function handleResponse(response) {
  if (response !== null) {
    username = response.account.username;
    authToken = response.accessToken;
    callBacks.forEach((x) => x(response));
  } else {
    selectAccount();
  }
}

export function signIn() {
  /**
   * You can pass a custom request object below. This will override the initial configuration. For more information, visit:
   * https://github.com/AzureAD/microsoft-authentication-library-for-js/blob/dev/lib/msal-browser/docs/request-response-object.md#request
   */

  myMSALObj.loginRedirect(loginRequest);
}

export function signOut() {
  /**
   * You can pass a custom request object below. This will override the initial configuration. For more information, visit:
   * https://github.com/AzureAD/microsoft-authentication-library-for-js/blob/dev/lib/msal-browser/docs/request-response-object.md#request
   */

  const logoutRequest = {
    account: myMSALObj.getAccountByUsername(username),
    postLogoutRedirectUri: msalConfig.auth.redirectUri,
  };

  myMSALObj.logoutRedirect(logoutRequest);
}

export function getUserName() {
  return username;
}

export function getAuthToken() {
  return authToken;
}

export function getTokenRedirect(request) {
  /**
   * See here for more info on account retrieval:
   * https://github.com/AzureAD/microsoft-authentication-library-for-js/blob/dev/lib/msal-common/docs/Accounts.md
   */
  request.account = myMSALObj.getAccountByUsername(username);

  return myMSALObj.acquireTokenSilent(request).catch((error) => {
    console.warn(
      "silent token acquisition fails. acquiring token using redirect"
    );
    if (error instanceof msal.InteractionRequiredAuthError) {
      // fallback to interaction when silent call fails
      return myMSALObj.acquireTokenRedirect(request);
    } else {
      console.warn(error);
    }
  });
}
