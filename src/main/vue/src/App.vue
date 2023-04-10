<script setup lang="ts">
import {RouterView} from 'vue-router';
</script>

<template>
    <v-layout>
        <v-app-bar title="TODO App"></v-app-bar>
        <v-main>
            <RouterView/>
        </v-main>
    </v-layout>
</template>
