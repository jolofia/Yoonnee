export interface IAgence {
    id?: string;
    nom?: string;
}

export class Agence implements IAgence {
    constructor(public id?: string, public nom?: string) {}
}
