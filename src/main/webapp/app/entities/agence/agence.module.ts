import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { YoonneeSharedModule } from 'app/shared';
import {
    AgenceComponent,
    AgenceDetailComponent,
    AgenceUpdateComponent,
    AgenceDeletePopupComponent,
    AgenceDeleteDialogComponent,
    agenceRoute,
    agencePopupRoute
} from './';

const ENTITY_STATES = [...agenceRoute, ...agencePopupRoute];

@NgModule({
    imports: [YoonneeSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [AgenceComponent, AgenceDetailComponent, AgenceUpdateComponent, AgenceDeleteDialogComponent, AgenceDeletePopupComponent],
    entryComponents: [AgenceComponent, AgenceUpdateComponent, AgenceDeleteDialogComponent, AgenceDeletePopupComponent],
    providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class YoonneeAgenceModule {
    constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
        this.languageHelper.language.subscribe((languageKey: string) => {
            if (languageKey !== undefined) {
                this.languageService.changeLanguage(languageKey);
            }
        });
    }
}