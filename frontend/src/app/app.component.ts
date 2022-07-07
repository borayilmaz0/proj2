import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { interval, Subscription } from 'rxjs';
import { EventService } from './service/event.service';
import { TokenService } from './service/token.service';


const source = interval(5000);

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  
  
  title: string = "";
  subscription: Subscription;

  constructor(private router: Router,
    private eventService: EventService,
    private tokenService: TokenService) {
    
    this.title = "Proj2";
    this.subscription = source.subscribe(val => {
      if (this.isLoggedIn())
      {
        this.eventService.updatePastEvents().subscribe(
          r => {},
          e => {this.router.navigate(["/login"])},
          () => {}
        )
      }
    });
  }
  
  navbarOpen = false;

  

  toggleNavbar() {
    this.navbarOpen = !this.navbarOpen;
  }

  isLoggedIn(): boolean {
    return localStorage.getItem("userId") != null;
  }
}
