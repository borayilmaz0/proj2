export class MailCreateRequest {
    mailinfo: string = "";
    adminId: number = -1;
    invitedUserId: number = -1;
    eventId: number = -1;

    constructor(mailinfo: string,
    adminId: number,
    invitedUserId: number,
    eventId: number)
    {
        this.mailinfo = mailinfo;
        this.adminId = adminId;
        this.invitedUserId = invitedUserId;
        this.eventId = eventId;
    }
}