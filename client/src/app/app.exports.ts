
import {storeFreeze} from "ngrx-store-freeze";
import {localStorageSync} from "ngrx-store-localstorage";
import {combineReducers, StoreModule} from "@ngrx/store";
import {appReducers} from "./shared/store/reducers/app.reducer";
import {AgmCoreModule} from "angular2-google-maps/core";
import {RouterStoreModule} from "@ngrx/router-store";
import {EffectsModule} from "@ngrx/effects";
import {AuthEffectService} from "./shared/store/effects/auth-effects.service";
import {UserEffectService} from "./shared/store/effects/user-effects.service";
import {RVEffectService} from "./shared/store/effects/rv-effects.service";
import {UIEffectService} from "./shared/store/effects/ui-effects.service";
import {StoreDevtoolsModule} from "@ngrx/store-devtools";
import {INITIAL_APP_STATE} from "./shared/store/state/application.state";
import {compose} from "@ngrx/core/compose";

export function myCompose(...args: any[]) {
  let functions:any = [];
  for (let _i = 0; _i < args.length; _i++) {
    functions[_i - 0] = args[_i];
  }
  return function (arg:any) {
    if (functions.length === 0) {
      return arg;
    }
    let last = functions[functions.length - 1];
    let rest = functions.slice(0, -1);
    return rest.reduceRight(function (composed:any, fn:any) { return fn(composed); }, last(arg));
  };
};

export const ngGoogleMapsConf = AgmCoreModule.forRoot({apiKey:'AIzaSyDSA-Sc8yoe_NIGqOwTGoNPiKge0KRK_wo',libraries:['places']});

export const syncedState = ['authState', 'storeData', 'router'];
//export function reducers() { return myCompose(storeFreeze, localStorageSync(syncedState, true), combineReducers)(appReducers) }

export const storeConf = StoreModule.provideStore(compose(storeFreeze, localStorageSync(syncedState, true), combineReducers)(appReducers), INITIAL_APP_STATE);
export const routerStoreConf = RouterStoreModule.connectRouter();
export const authEffectsConf = EffectsModule.run(AuthEffectService);
export const userEffectsConf = EffectsModule.run(UserEffectService);
export const rvEffectsConf = EffectsModule.run(RVEffectService);
export const postEffectsConf = EffectsModule.run(UIEffectService);
export const storeDevToolsConf = StoreDevtoolsModule.instrumentOnlyWithExtension();
