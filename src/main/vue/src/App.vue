<script setup lang="ts">
import {RouterView} from 'vue-router';
</script>
<script lang="ts">
import {RouterView} from 'vue-router';
import {defineComponent} from "vue";
// @ts-ignore
import * as auth from "./auth/authRedirect.js";

export default defineComponent({
    data() {
        return { loggedIn: false };
    },

    mounted() {
        auth.registerCallback((_: any) => {
            this.loggedIn = true;
        });
    }
});
</script>

<template>
    <v-layout v-if="loggedIn">
        <v-app-bar title="Margic TODO-List"></v-app-bar>
        <v-main>
            <RouterView/>
        </v-main>
    </v-layout>
</template>
