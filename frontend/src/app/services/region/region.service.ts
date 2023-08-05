import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Region } from 'src/app/models/region';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root',
})
export class RegionService {
  private url = environment.baseUrl + '/regions';

  constructor(private http: HttpClient) {}

  getRegions(): Observable<Region[]> {
    return this.http.get<Region[]>(this.url);
  }
}
