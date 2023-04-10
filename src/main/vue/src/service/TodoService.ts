export class Todo {
    public id: number;
    public title: string;
    public description: string;
    public done: boolean;

    constructor(id: number, title: string, description: string, done: boolean) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.done = done;
    }

}

export default class TodoService {

    todos = [] as Todo[];

    public async getAllTodos() {
        return this.todos;
    }

    public async addTodo(newTodo: {title: string, description: string}) {
        this.todos.push(new Todo(this.todos.length + 1, newTodo.title, newTodo.description, false));
    }

    public async resolveTodo(id: number) {
        const foundTodo = this.todos.find(it => it.id === id);
        if(foundTodo) {
            foundTodo.done = true;
        }
    }
}
