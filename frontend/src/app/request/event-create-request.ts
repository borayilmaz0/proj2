export class EventCreateRequest
{
    dueYear: number = new Date().getFullYear();
    dueMonth: number = new Date().getMonth();
    dueDay: number = new Date().getUTCDate();
    dueHour: number = new Date().getHours();
    dueMin: number = new Date().getMinutes();
    text: string = "";
    title: string = "";
    adminid!: number;
    monthly: boolean = false;
}