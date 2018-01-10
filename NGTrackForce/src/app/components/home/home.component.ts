/**
 * @author Nasir Alauddin
 */

import { Component, OnInit } from '@angular/core';
import { HomeService } from '../../services/home-service/home-service';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {

  button: string;
  data: {};

  constructor(homeService: HomeService) { }

  ngOnInit() {
    this.button = 'Populate Database';
  }

  populateDB(){
    if (this.button === 'Populate Database'){
      this.button = 'Populate Static SalesForce';
    } else {
      this.button = 'Populate Database';
    }
  }

  refresh(){
    this.button = 'Populate Database';
  }

}
