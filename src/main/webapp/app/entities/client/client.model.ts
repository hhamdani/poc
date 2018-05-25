import { BaseEntity } from './../../shared';

export const enum ClientType {
    'PERSONNE_PHYSIQUE',
    'PERSONNE_MORALE'
}

export class Client implements BaseEntity {
    constructor(
        public id?: number,
        public nom?: string,
        public prenom?: string,
        public type?: ClientType,
        public cin?: string,
        public dateNaissance?: any,
        public telephone?: string,
        public email?: string,
        public comptes?: BaseEntity[],
        public compteId?: number,
    ) {
    }
}
