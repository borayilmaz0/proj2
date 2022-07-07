export class UserResponse {
    id: number = -1;
    username: string = "";

    constructor(id: number, username: string)
    {
        this.id = id;
        this.username = username;
    }
}