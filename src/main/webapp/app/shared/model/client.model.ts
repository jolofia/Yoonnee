export interface IClient {
    id?: string;
    nom?: string;
}

export class Client implements IClient {
    constructor(public id?: string, public nom?: string) {}
}
