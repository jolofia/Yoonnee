export interface IAgence {
    id?: string;
    nom?: string;
}

export class Agence implements IAgence {
    constructor(public id?: string, public nom?: string) {}
}

// export interface IAgence {
//     id?: string;
//     nom: string;
//     adresse: string;
//     ville: string;
//     pays: string;
//     telephone: string;
//     gerant: string[];
// }
//
// export class Agence implements IAgence {
//     adresse: string;
//     gerant: string[];
//     id: string;
//     nom: string;
//     pays: string;
//     telephone: string;
//     ville: string;
// }
