import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { EventUpdateRequest } from "../request/event-update-request";
import { Event } from 'src/app/model/event';
import { TokenService } from "./token.service";
import { EventResponse } from "../response/event-response";
import { EventCreateRequest } from "../request/event-create-request";
import { switchMap } from "rxjs";


@Injectable({
    providedIn: 'root'
})
export class EventService {
    
    private eventsUrl: string;

    constructor(private http: HttpClient,
        private tokenService: TokenService) 
    {
        this.eventsUrl = 'http://localhost:8080/events';
    }

    findAll()
    {
        return this.getUserToken().pipe(switchMap(r => { 
            return this.http.get<EventResponse[]>(this.eventsUrl, {headers:{Authorization: "Bearer " + r.latestToken}})
        }))
    }

    findAllByUserId(userId: number)
    {
        return this.getUserToken().pipe(switchMap(r => { 
            return this.http.get<EventResponse[]>(this.eventsUrl + "?userId=" + userId, {headers:{Authorization: "Bearer " + r.latestToken}})
        }))
    }

    findById(id: number)
    {
        return this.getUserToken().pipe(switchMap(r => { 
            return this.http.get<EventResponse>(this.eventsUrl + "/" + id, {headers:{Authorization: "Bearer " + r.latestToken}})
        }))
    }

    postEvent(event: EventCreateRequest)
    {
        return this.getUserToken().pipe(switchMap(r => { 
            return this.http.post<EventResponse>(this.eventsUrl, event, {headers:{Authorization: "Bearer " + r.latestToken}})
        }))
    }

    findRelatedEvents(userId: number)
    {
        return this.getUserToken().pipe(switchMap(r => { 
            return this.http.get<EventResponse[]>(this.eventsUrl + "/findRelatedEvents/" + userId, {headers:{Authorization: "Bearer " + r.latestToken}})
        }))
    }

    updateEvent(eventUpdateRequest: EventUpdateRequest)
    {
        return this.getUserToken().pipe(switchMap(r => { 
            return this.http.put<EventResponse>(this.eventsUrl, eventUpdateRequest, {headers:{Authorization: "Bearer " + r.latestToken}})
        }))
    }

    deleteEvent(id: number)
    {
        return this.getUserToken().pipe(switchMap(r => { 
            return this.http.delete<void>(this.eventsUrl + "/" + id, {headers:{Authorization: "Bearer " + r.latestToken}})
        }))
    }

    updatePastEvents()
    {
        return this.getUserToken().pipe(switchMap(r => { 
            return this.http.patch<EventResponse[]>(this.eventsUrl + "/updatePastEvents", {}, {headers:{Authorization: "Bearer " + r.latestToken}})
        }))
        return this.http.patch<EventResponse[]>(this.eventsUrl + "/updatePastEvents", {});
    }

    findAllUpcomingEvents()
    {
        return this.getUserToken().pipe(switchMap(r => { 
            return this.http.get<EventResponse[]>(this.eventsUrl + "/orderedByDueAscending", {headers:{Authorization: "Bearer " + r.latestToken}})
          }))
    }

    private getUserToken()
    {
        return this.tokenService.getTokenByUserId();
    }
}