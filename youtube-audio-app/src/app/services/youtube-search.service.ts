import { Injectable } from '@angular/core';
import { Http } from '@angular/http';
import 'rxjs/add/operator/map';


@Injectable()
export class YoutubeSearchService {

  private  _yTBSearchUrl = 'http://localhost:8080/simple-audio-webapp/webapi/serach';

  constructor(private _http: Http) {}

  getSearchResult(keyword:string) {
     const _url = this._yTBSearchUrl + '/' + keyword;
      console.log(_url);
     return this._http.get(_url).map(_result => _result.json());
  }

}
