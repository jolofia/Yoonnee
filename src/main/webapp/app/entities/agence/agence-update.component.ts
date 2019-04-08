import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { FormArray, FormBuilder, FormGroup } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Gerant, IAgence } from 'app/shared/model/agence.model';
import { Observable } from 'rxjs';
import { AgenceService } from './agence.service';

@Component({
    selector: 'jhi-agence-update',
    templateUrl: './agence-update.component.html'
})
export class AgenceUpdateComponent implements OnInit {
    agence: IAgence;
    isSaving: boolean;
    form: FormGroup;

    constructor(private agenceService: AgenceService, private activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

    get gerants(): FormArray {
        return this.form.get('gerants') as FormArray;
    }

    ngOnInit() {
        this.isSaving = false;

        this.activatedRoute.data.subscribe(({ agence }) => {
            console.log(agence);
            this.agence = agence;
            this.form = this.fb.group({
                id: [this.agence.id],
                nom: [this.agence.nom],
                adresse: [this.agence.adresse],
                ville: [this.agence.ville],
                pays: [this.agence.pays],
                telephone: [this.agence.telephone],
                gerants: this.fb.array([])
            });
            this.addGerant(this.agence.gerants);
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        this.agence = this.form.value;
        if (this.agence.id) {
            this.subscribeToSaveResponse(this.agenceService.update(this.agence));
        } else {
            this.subscribeToSaveResponse(this.agenceService.create(this.agence));
        }
    }

    addGerant(gerants?: Gerant[]) {
        if (gerants) {
            gerants.forEach(g => {
                this.gerants.push(this.createGerantControl(g));
            });
        } else {
            this.gerants.push(this.createGerantControl());
        }
    }

    delGerant(index) {
        this.gerants.removeAt(index);
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IAgence>>) {
        result.subscribe((res: HttpResponse<IAgence>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    protected onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    protected onSaveError() {
        this.isSaving = false;
    }

    private createGerantControl(gerant?: Gerant) {
        if (!gerant) {
            gerant = {};
        }
        return this.fb.group({
            name: [gerant.name],
            telephone: [gerant.telephone],
            email: [gerant.email]
        });
    }
}
