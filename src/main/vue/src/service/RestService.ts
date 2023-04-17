import type {Todo} from "@/service/TodoService";

export default class RestService {

    async get(path: string) {
        return await this.fetch(path, "GET", undefined);
    }

    async post(path: string, payload: any) {
        return await this.fetch(path, "POST", payload);
    }

    async put(path: string, payload: any) {
        return await this.fetch(path, "PUT", payload);
    }

    private getDefaultHeader = () =>
        new Headers({
            //Authorization: "Bearer " + getAuthToken(),
            "Content-Type": "application/json",
        });


    private async fetch(path: string, method: string, payload: any) {
        const response = await fetch(import.meta.env.VITE_APP_BASE_URL + path, {
            method: method,
            headers: this.getDefaultHeader(),
            body: payload ? JSON.stringify(payload) : undefined,
        });

        let responseContent = await response.text();
        return responseContent ? JSON.parse(responseContent) : {};
    }
}
