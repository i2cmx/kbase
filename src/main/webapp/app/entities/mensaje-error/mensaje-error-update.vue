<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <form name="editForm" role="form" novalidate v-on:submit.prevent="save()" >
                <h2 id="kbaseApp.mensajeError.home.createOrEditLabel" v-text="$t('kbaseApp.mensajeError.home.createOrEditLabel')">Create or edit a MensajeError</h2>
                <div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('kbaseApp.mensajeError.clave')" for="mensaje-error-clave">Clave</label>
                        <b-form-input
                            name="clave" 
                            id="mensaje-error-clave" 
                            :state="!$v.mensajeError.clave.$invalid" 
                            :placeholder="$t('kbaseApp.mensajeError.detail.codigoPlaceHolder')" v-model.trim="clave"></b-form-input>
                        <div v-if="$v.mensajeError.clave.$invalid">
                            <small class="form-text text-danger" v-if="!$v.mensajeError.clave.required" v-text="$t('entity.validation.required')">
                                This field is required.
                            </small>
                            <small class="form-text text-danger" v-if="!$v.mensajeError.clave.minLength">
                                {{$t('entity.validation.minlength', { min: '4' })}}
                            </small>
                            <small class="form-text text-danger" v-if="!$v.mensajeError.clave.maxLength">
                                {{$t('entity.validation.maxlength', { max: '50' })}}
                            </small>
                            <small class="form-text text-danger" v-if="!$v.mensajeError.clave.isUnique">
                                el usuario ya existe
                            </small>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('kbaseApp.mensajeError.desc')" for="mensaje-error-desc">Desc</label>
                        <b-form-textarea 
                            :state="!$v.mensajeError.desc.$invalid"
                            name="desc"
                            rows="5"
                            class="form-control" 
                            id="mensaje-error-desc" 
                            :placeholder="$t('kbaseApp.mensajeError.detail.descripcionPlaceHolder')"
                            v-model.trim="$v.mensajeError.desc.$model">
                        </b-form-textarea>
                        <div v-if="$v.mensajeError.desc.$invalid">
                            <small class="form-text text-danger" v-if="!$v.mensajeError.desc.required" v-text="$t('entity.validation.required')">
                                This field is required.
                            </small>
                            <small class="form-text text-danger" v-if="!$v.mensajeError.desc.minLength">
                                {{$t('entity.validation.minlength', { min: '4' })}}
                            </small>
                            <small class="form-text text-danger" v-if="!$v.mensajeError.desc.maxLength">
                                {{$t('entity.validation.maxlength', { max: '500' })}}
                            </small>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('kbaseApp.mensajeError.instruccion')" for="mensaje-error-desc">Desc</label>
                        <b-form-textarea 
                            :state="!$v.mensajeError.instruccion.desc.$invalid"
                            name="desc"
                            rows="5"
                            class="form-control" 
                            id="mensaje-error-desc" 
                            :placeholder="$t('kbaseApp.mensajeError.detail.descripcionPlaceHolder')"
                            v-model.trim="$v.mensajeError.instruccion.desc.$model">
                        </b-form-textarea>
                        <div v-if="$v.mensajeError.instruccion.desc.$invalid">
                            <small class="form-text text-danger" v-if="!$v.mensajeError.instruccion.desc.required" v-text="$t('entity.validation.required')">
                                This field is required.
                            </small>
                            <small class="form-text text-danger" v-if="!$v.mensajeError.instruccion.desc.minLength">
                                {{$t('entity.validation.minlength', { min: '4' })}}
                            </small>
                            <small class="form-text text-danger" v-if="!$v.mensajeError.instruccion.desc.maxLength">
                                {{$t('entity.validation.maxlength', { max: '500' })}}
                            </small>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('kbaseApp.mensajeError.pasos')" for="mensaje-error-pasos">Pasos</label>
                        <div class="table-responsive">
                            <b-button v-on:click="prepareToAddStep()" 
                                    class="btn btn-sm"
                                    variant="primary">Agregar Paso</b-button>
                            <table class="table">
                                <tbody>
                                    <draggable v-if="mensajeError.instruccion" v-model.trim="mensajeError.instruccion.pasos" 
                                    @change="orderSteps"
                                    @start="drag=true"
                                    @end="drag=false">
                                        <tr v-for="step in mensajeError.instruccion.pasos" :key="step.paso">
                                            <td><b>{{step.paso}}</b></td>
                                            <td>{{step.desc}}</td>
                                            <td class="text-right">
                                                <div class="btn-group">
                                                    <b-button v-on:click="prepareToSave(step)"
                                                        variant="outline-primary"> 
                                                        Editar
                                                    </b-button>
                                                    <b-button v-on:click="prepareRemove(step)"
                                                        variant="outline-danger"
                                                        class="btn btn-sm"
                                                        v-b-modal.removeEntity>
                                                        Eliminar
                                                    </b-button>
                                                </div>
                                            </td>
                                        </tr>
                                    </draggable>
                                </tbody>
                            </table>
                        </div>
                    </div>
                </div>
                <div>
                    <button type="button" id="cancel-save" class="btn btn-secondary" v-on:click="previousState()">
                        <font-awesome-icon icon="ban"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.cancel')">Cancel</span>
                    </button>
                    <button type="submit" id="save-entity" :disabled="$v.mensajeError.$invalid || isSaving" class="btn btn-primary">
                        <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
                    </button>
                </div>
            </form>
        </div>
        <b-modal ref="addStep" id="addStep" >
            <span slot="modal-title"><span id="kbaseApp.mensajeError.delete.question">Agregar Paso</span></span>
            <div class="form-group">
                <b-form-textarea 
                    :state="!$v.newStep.desc.$invalid"
                    name="desc" 
                    rows="5"
                    class="form-control"
                    id="mensaje-error-desc"
                    v-model.trim="newStep.desc">
                </b-form-textarea>
                <div v-if="$v.newStep.desc.$invalid">
                    <small class="form-text text-danger" v-if="!$v.newStep.desc.required" v-text="$t('entity.validation.required')">
                        This field is required.
                    </small>
                    <small class="form-text text-danger" v-if="!$v.newStep.desc.minLength">
                        {{$t('entity.validation.minlength', { min: '4' })}}
                    </small>
                    <small class="form-text text-danger" v-if="!$v.newStep.desc.maxLength">
                        {{$t('entity.validation.maxlength', { max: '500' })}}
                    </small>
                </div>
            </div>
            <div slot="modal-footer">
                <b-button variant="outline-dark" v-text="$t('entity.action.cancel')" v-on:click="closeAddStepDialog()"></b-button>
                <b-button :disabled="$v.newStep.desc.$invalid" variant="outline-primary" v-text="$t('entity.action.save')" v-on:click="addStep()"></b-button>
            </div>
        </b-modal>
        <b-modal ref="editStep" id="editStep" >
            <span slot="modal-title"><span id="kbaseApp.mensajeError.delete.question">Editar Paso {{newStep.paso}}</span></span>
            <div class="form-group">
                <b-form-textarea 
                    :state="!$v.newStep.desc.$invalid"
                    name="desc" 
                    rows="5"
                    class="form-control" 
                    id="mensaje-error-desc"
                    v-model.trim="newStep.desc">
                </b-form-textarea>
                <div v-if="$v.newStep.desc.$invalid">
                    <small class="form-text text-danger" v-if="!$v.newStep.desc.required" v-text="$t('entity.validation.required')">
                        This field is required.
                    </small>
                    <small class="form-text text-danger" v-if="!$v.newStep.desc.minLength">
                        {{$t('entity.validation.minlength', { min: '4' })}}
                    </small>
                    <small class="form-text text-danger" v-if="!$v.newStep.desc.maxLength">
                        {{$t('entity.validation.maxlength', { max: '500' })}}
                    </small>
                </div>
            </div>
            <div slot="modal-footer">
                <b-button variant="outline-dark" v-text="$t('entity.action.cancel')" v-on:click="closeEditStepDialog()"></b-button>
                <b-button :disabled="$v.newStep.desc.$invalid" variant="outline-primary" v-text="$t('entity.action.save')" v-on:click="saveStep()"></b-button>
            </div>
        </b-modal>
        <b-modal ref="removeEntity" id="removeEntity" >
            <span slot="modal-title"><span id="kbaseApp.mensajeError.delete.question" v-text="$t('entity.delete.title')">Confirm delete operation</span></span>
            <div class="modal-body">
                <p id="jhi-delete-mensajeError-heading" v-bind:title="$t('kbaseApp.mensajeError.delete.question')">Are you sure you want to delete this Mensaje Error?</p>
            </div>
            <div slot="modal-footer">
                <b-button variant="outline-dark" v-text="$t('entity.action.cancel')" v-on:click="closeDialog()">Danger</b-button>
                <b-button variant="outline-danger" v-text="$t('entity.action.delete')" v-on:click="removeStep()">Danger</b-button>
            </div>
        </b-modal>
    </div>
</template>
<script lang="ts" src="./mensaje-error-update.component.ts">
</script>
