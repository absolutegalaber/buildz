import {Injectable} from "@angular/core";
import {ActivatedRouteSnapshot, CanActivate, RouterStateSnapshot} from "@angular/router";
import "rxjs/add/operator/catch";
import "rxjs/add/observable/of";
import {Observable} from "rxjs/Observable";
import {Http, Response} from "@angular/http";
import {IEnvironment} from "../../core/domain";
import {BuildzDispatcher} from "../../core/services/buildz-dispatcher-service";
@Injectable()
export class LoadEnvironmentGuard implements CanActivate {

  constructor(private http: Http, private dispatcher: BuildzDispatcher) {
  }

  canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<boolean> {
    let environmentName = route.params['environmentName'];
    return this.http.get(`/v1/environments/${environmentName}`)
      .map((res: Response) => {
        this.dispatcher.environmentLoaded(res.json() as IEnvironment);
        this.dispatcher.verifyEnvironment();
        return true;
      }).catch((err: Response) => Observable.of(false));
  }
}
