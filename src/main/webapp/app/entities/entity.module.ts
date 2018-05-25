import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

import { PocClientModule } from './client/client.module';
import { PocCompteModule } from './compte/compte.module';
/* jhipster-needle-add-entity-module-import - JHipster will add entity modules imports here */

@NgModule({
    imports: [
        PocClientModule,
        PocCompteModule,
        /* jhipster-needle-add-entity-module - JHipster will add entity modules here */
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class PocEntityModule {}
