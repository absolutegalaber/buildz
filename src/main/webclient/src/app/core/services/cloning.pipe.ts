import {Pipe, PipeTransform} from "@angular/core";
import {cloneIt} from "../store/clone";

@Pipe({name: 'cloneIt'})
export class CloningPipe implements PipeTransform {

  transform(value: any, ...args): any {
    return cloneIt(value);
  }
}
