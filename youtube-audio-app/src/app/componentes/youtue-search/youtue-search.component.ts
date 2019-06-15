import { Component, OnInit } from '@angular/core';
import {YoutubeSearchService} from '../../services/youtube-search.service';
@Component({
  selector: 'app-youtue-search',
  templateUrl: './youtue-search.component.html',
  styleUrls: ['./youtue-search.component.css']
})
export class YoutueSearchComponent implements OnInit {
  data = [];
  keyword:string;
  _keyword:string;

  _youtubeSearchVar: YoutubeSearchService;
  constructor(_youtubeSearch: YoutubeSearchService) {
    this._youtubeSearchVar=_youtubeSearch;
  }
  getSearch() {
    console.log(this.keyword);
    this._youtubeSearchVar.getSearchResult(this.keyword).subscribe(_result => (this.data = _result) );
    this._keyword=this.keyword;
  }
  ngOnInit() {}
  showWindow(url: string) {
  const windoUrl = 'http://localhost:8080/simple-audio-webapp/webapi/myresource/';
  const windowAttr = 'toolbar=no,scrollbars=no,resizable=no,Location=no,toolbar=0,toolbar=no,titlebar=no';
  const windowAttr2 = 'top=20,left=20,width=400,height=50';
  const _myWindowUrl = windoUrl + url;
      window.open(_myWindowUrl, '_blank', windowAttr  + ',' + windowAttr2);

  }

}
