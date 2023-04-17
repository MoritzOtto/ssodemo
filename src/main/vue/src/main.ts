import { createApp } from 'vue';
import App from './App.vue';
import router from './router';

import 'vuetify/styles';
import {createVuetify} from "vuetify";
import * as components from 'vuetify/components';
import * as directives from 'vuetify/directives';
// @ts-ignore
import * as auth from "./auth/authRedirect.js";

const vuetify = createVuetify({
    components,
    directives
});

auth.signIn();

const app = createApp(App);

app.use(router);
app.use(vuetify);

app.mount('#app');
