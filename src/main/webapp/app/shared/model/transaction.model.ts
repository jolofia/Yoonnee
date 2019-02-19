export interface ITransaction {
    id?: string;
    emetteur?: string;
    agenceEmetteur?: string;
    recepteur?: string;
    agenceRecepteur?: string;
    montantEnvoye?: number;
    comission?: number;
    montantRecu?: number;
}

export class Transaction implements ITransaction {
    constructor(
        public id?: string,
        public emetteur?: string,
        public agenceEmetteur?: string,
        public recepteur?: string,
        public agenceRecepteur?: string,
        public montantEnvoye?: number,
        public comission?: number,
        public montantRecu?: number
    ) {}
}
