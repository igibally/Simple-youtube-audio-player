import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { HttpModule } from '@angular/http';
import { FormsModule } from '@angular/forms';

import { AppComponent } from './app.component';
import { YoutueSearchComponent } from './componentes/youtue-search/youtue-search.component';
import { YoutubeSearchService } from './services/youtube-search.service';

@NgModule({
  declarations: [AppComponent, YoutueSearchComponent],
  imports: [BrowserModule , HttpModule , FormsModule],
  providers: [YoutubeSearchService],
  bootstrap: [AppComponent]
})
export class AppModule {}
