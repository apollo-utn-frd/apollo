
import {compose} from "@ngrx/core";
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
import {PostEffectService} from "./shared/store/effects/posts-effects.service";
import {StoreDevtoolsModule} from "@ngrx/store-devtools";
import {INITIAL_APP_STATE} from "./shared/store/state/application.state";

export const ngGoogleMapsConf = AgmCoreModule.forRoot({apiKey:'AIzaSyDSA-Sc8yoe_NIGqOwTGoNPiKge0KRK_wo',libraries:['places']});

export const syncedState = ['authState', 'storeData', 'router'];
export function reducers() { return compose(storeFreeze, localStorageSync(syncedState, true), combineReducers)(appReducers); }

export const storeConf = StoreModule.provideStore(reducers, INITIAL_APP_STATE);
export const routerStoreConf = RouterStoreModule.connectRouter();
export const authEffectsConf = EffectsModule.run(AuthEffectService);
export const userEffectsConf = EffectsModule.run(UserEffectService);
export const rvEffectsConf = EffectsModule.run(RVEffectService);
export const postEffectsConf = EffectsModule.run(PostEffectService);
export const storeDevToolsConf = StoreDevtoolsModule.instrumentOnlyWithExtension();
