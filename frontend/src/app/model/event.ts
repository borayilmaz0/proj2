export class Event {
    id: number = -1;
    due: Date = new Date();
    sent: Date = new Date();
    text: string = "";
    title: string = "";
    adminid: number = -1;
    monthly: boolean = true;

    constructor() {}
}