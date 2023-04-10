import RestService from "@/service/RestService";

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

    restService: RestService;
    todos = [] as Todo[];

    constructor() {
        this.restService = new RestService();
    }


    public async getAllTodos(): Promise<Todo[]> {
        return await this.restService.get("todo");
    }

    public async addTodo(newTodo: {title: string, description: string}): Promise<void> {
        return await this.restService.post("todo", newTodo);
    }

    public async resolveTodo(id: number): Promise<void> {
        const todo = await this.restService.get(`todo/${id}`);
        console.log(todo);
        todo.done = true;
        await this.restService.put("todo", todo);
    }
}
