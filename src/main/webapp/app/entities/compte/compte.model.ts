import { BaseEntity } from './../../shared';

export const enum CompteType {
    'ORDINAIRE',
    'VIP'
}

export class Compte implements BaseEntity {
    constructor(
        public id?: number,
        public reference?: string,
        public dateOuv?: any,
        public adresse?: string,
        public type?: CompteType,
        public actif?: boolean,
        public clientId?: number,
        public clients?: BaseEntity[],
    ) {
        this.actif = false;
    }
}
