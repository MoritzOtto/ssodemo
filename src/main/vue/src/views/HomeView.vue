<script lang="ts">
import {defineComponent} from "vue";
import TodoService, {Todo} from "@/service/TodoService";

const todoService = new TodoService();

export default defineComponent({
    data() {
        return {
            dialogOpen: false,
            newTodo: {
                title: "",
                description: ""
            },
            showDone: false,
            todos: [] as Todo[]
        }
    },

    mounted() {
        this.reload();
    },

    methods: {
        async reload() {
            let todos = await todoService.getAllTodos();
            if (!this.showDone) {
                todos = todos.filter(it => !it.done);
            }
            this.todos.splice(0, this.todos.length);
            Object.assign(this.todos, todos);
        },
        async saveTitle(todo: { title: string, description: string }) {
            this.newTodo = {title: "", description: ""};


            await todoService.addTodo(todo);
            await this.reload();
            this.dialogOpen = false;
        },
        async resolve(id: number) {
            await todoService.resolveTodo(id);
            await this.reload();
        }
    }
});

</script>

<template>
    <main>
        <v-row justify="space-around" justify-lg="center" class="py-5" align="center">
            <v-col cols="9" lg="5">
                <v-text-field v-model="newTodo.title" label="Neues Todo" class="pt-5" outlined clearable></v-text-field>
            </v-col>
            <v-col cols="2" lg="3">
                <v-dialog
                        v-model="dialogOpen">
                    <template v-slot:activator="{ props }">
                        <v-btn
                                color="primary"
                                v-bind="props"
                        >
                            Todo Anlegen
                        </v-btn>
                    </template>

                    <v-card class="ma-5">
                        <v-card-text>
                            <v-text-field v-model="newTodo.title" label="Titel" class="pt-5" outlined
                                          clearable></v-text-field>
                            <v-textarea v-model="newTodo.description" label="Beschreibung" class="pt-5" outlined
                                        clearable></v-textarea>
                        </v-card-text>
                        <v-card-actions>
                            <v-btn color="primary" block @click="saveTitle(newTodo)">Speichern</v-btn>
                        </v-card-actions>
                    </v-card>
                </v-dialog>
            </v-col>
        </v-row>
        <v-container fluid>
            <input @change="reload()" id="doneOnes" v-model="showDone" type="checkbox"/>
            <label for="doneOnes">
                Erledigte Anzeigen
            </label>
            <v-row dense>
                <v-col
                        v-for="todo in todos"
                        v-bind:key="todo.id"
                        cols="12"
                        md="6"
                        lg="4"
                        class="pa-3"
                >
                    <v-card>
                        <v-card-title v-text="todo.title"></v-card-title>
                        <v-card-text>{{ todo.description }}</v-card-text>
                        <v-card-actions>
                            <v-btn color="primary" block @click="resolve(todo.id)" v-if="!todo.done">Erledigen</v-btn>
                            <v-btn color="primary" block v-if="todo.done" disabled>Erledigt</v-btn>
                        </v-card-actions>

                    </v-card>
                </v-col>
            </v-row>
        </v-container>
    </main>
</template>
