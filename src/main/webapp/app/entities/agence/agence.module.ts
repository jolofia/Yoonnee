import { CUSTOM_ELEMENTS_SCHEMA, NgModule } from '@angular/core';
import { ReactiveFormsModule } from '@angular/forms';
import { RouterModule } from '@angular/router';
import { JhiLanguageHelper } from 'app/core';

import { YoonneeSharedModule } from 'app/shared';
import { JhiLanguageService } from 'ng-jhipster';
import {
    AgenceComponent,
    AgenceDeleteDialogComponent,
    AgenceDeletePopupComponent,
    AgenceDetailComponent,
    agencePopupRoute,
    agenceRoute,
    AgenceUpdateComponent
} from './';

const ENTITY_STATES = [...agenceRoute, ...agencePopupRoute];

@NgModule({
    imports: [YoonneeSharedModule, RouterModule.forChild(ENTITY_STATES), ReactiveFormsModule],
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
