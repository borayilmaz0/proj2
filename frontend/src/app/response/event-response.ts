export class EventResponse {
    id: number = -1;
    title: string = "";
    sent: Date = new Date();
    due: Date = new Date();
    text: string = "";
    adminid: number = -1;
    adminUsername: string = "";
    isMonthly: boolean = false;
}