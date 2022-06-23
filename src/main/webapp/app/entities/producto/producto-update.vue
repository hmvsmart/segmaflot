<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" role="form" novalidate v-on:submit.prevent="save()">
        <h2
          id="segmaflotApp.producto.home.createOrEditLabel"
          data-cy="ProductoCreateUpdateHeading"
          v-text="$t('segmaflotApp.producto.home.createOrEditLabel')"
        >
          Create or edit a Producto
        </h2>
        <div>
          <div class="form-group" v-if="producto.id">
            <label for="id" v-text="$t('global.field.id')">ID</label>
            <input type="text" class="form-control" id="id" name="id" v-model="producto.id" readonly />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('segmaflotApp.producto.nombre')" for="producto-nombre">Nombre</label>
            <input
              type="text"
              class="form-control"
              name="nombre"
              id="producto-nombre"
              data-cy="nombre"
              :class="{ valid: !$v.producto.nombre.$invalid, invalid: $v.producto.nombre.$invalid }"
              v-model="$v.producto.nombre.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('segmaflotApp.producto.marca')" for="producto-marca">Marca</label>
            <input
              type="text"
              class="form-control"
              name="marca"
              id="producto-marca"
              data-cy="marca"
              :class="{ valid: !$v.producto.marca.$invalid, invalid: $v.producto.marca.$invalid }"
              v-model="$v.producto.marca.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('segmaflotApp.producto.tipo')" for="producto-tipo">Tipo</label>
            <input
              type="text"
              class="form-control"
              name="tipo"
              id="producto-tipo"
              data-cy="tipo"
              :class="{ valid: !$v.producto.tipo.$invalid, invalid: $v.producto.tipo.$invalid }"
              v-model="$v.producto.tipo.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('segmaflotApp.producto.cantidad')" for="producto-cantidad">Cantidad</label>
            <input
              type="number"
              class="form-control"
              name="cantidad"
              id="producto-cantidad"
              data-cy="cantidad"
              :class="{ valid: !$v.producto.cantidad.$invalid, invalid: $v.producto.cantidad.$invalid }"
              v-model.number="$v.producto.cantidad.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('segmaflotApp.producto.unidadMedida')" for="producto-unidadMedida"
              >Unidad Medida</label
            >
            <input
              type="text"
              class="form-control"
              name="unidadMedida"
              id="producto-unidadMedida"
              data-cy="unidadMedida"
              :class="{ valid: !$v.producto.unidadMedida.$invalid, invalid: $v.producto.unidadMedida.$invalid }"
              v-model="$v.producto.unidadMedida.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('segmaflotApp.producto.material')" for="producto-material">Material</label>
            <input
              type="text"
              class="form-control"
              name="material"
              id="producto-material"
              data-cy="material"
              :class="{ valid: !$v.producto.material.$invalid, invalid: $v.producto.material.$invalid }"
              v-model="$v.producto.material.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('segmaflotApp.producto.color')" for="producto-color">Color</label>
            <input
              type="text"
              class="form-control"
              name="color"
              id="producto-color"
              data-cy="color"
              :class="{ valid: !$v.producto.color.$invalid, invalid: $v.producto.color.$invalid }"
              v-model="$v.producto.color.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('segmaflotApp.producto.uso')" for="producto-uso">Uso</label>
            <input
              type="text"
              class="form-control"
              name="uso"
              id="producto-uso"
              data-cy="uso"
              :class="{ valid: !$v.producto.uso.$invalid, invalid: $v.producto.uso.$invalid }"
              v-model="$v.producto.uso.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('segmaflotApp.producto.descripcion')" for="producto-descripcion"
              >Descripcion</label
            >
            <div>
              <div v-if="producto.descripcion" class="form-text text-danger clearfix">
                <a
                  class="pull-left"
                  v-on:click="openFile(producto.descripcionContentType, producto.descripcion)"
                  v-text="$t('entity.action.open')"
                  >open</a
                ><br />
                <span class="pull-left">{{ producto.descripcionContentType }}, {{ byteSize(producto.descripcion) }}</span>
                <button
                  type="button"
                  v-on:click="
                    producto.descripcion = null;
                    producto.descripcionContentType = null;
                  "
                  class="btn btn-secondary btn-xs pull-right"
                >
                  <font-awesome-icon icon="times"></font-awesome-icon>
                </button>
              </div>
              <input
                type="file"
                ref="file_descripcion"
                id="file_descripcion"
                data-cy="descripcion"
                v-on:change="setFileData($event, producto, 'descripcion', false)"
                v-text="$t('entity.action.addblob')"
              />
            </div>
            <input
              type="hidden"
              class="form-control"
              name="descripcion"
              id="producto-descripcion"
              data-cy="descripcion"
              :class="{ valid: !$v.producto.descripcion.$invalid, invalid: $v.producto.descripcion.$invalid }"
              v-model="$v.producto.descripcion.$model"
            />
            <input
              type="hidden"
              class="form-control"
              name="descripcionContentType"
              id="producto-descripcionContentType"
              v-model="producto.descripcionContentType"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('segmaflotApp.producto.otros')" for="producto-otros">Otros</label>
            <div>
              <div v-if="producto.otros" class="form-text text-danger clearfix">
                <a class="pull-left" v-on:click="openFile(producto.otrosContentType, producto.otros)" v-text="$t('entity.action.open')"
                  >open</a
                ><br />
                <span class="pull-left">{{ producto.otrosContentType }}, {{ byteSize(producto.otros) }}</span>
                <button
                  type="button"
                  v-on:click="
                    producto.otros = null;
                    producto.otrosContentType = null;
                  "
                  class="btn btn-secondary btn-xs pull-right"
                >
                  <font-awesome-icon icon="times"></font-awesome-icon>
                </button>
              </div>
              <input
                type="file"
                ref="file_otros"
                id="file_otros"
                data-cy="otros"
                v-on:change="setFileData($event, producto, 'otros', false)"
                v-text="$t('entity.action.addblob')"
              />
            </div>
            <input
              type="hidden"
              class="form-control"
              name="otros"
              id="producto-otros"
              data-cy="otros"
              :class="{ valid: !$v.producto.otros.$invalid, invalid: $v.producto.otros.$invalid }"
              v-model="$v.producto.otros.$model"
            />
            <input
              type="hidden"
              class="form-control"
              name="otrosContentType"
              id="producto-otrosContentType"
              v-model="producto.otrosContentType"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('segmaflotApp.producto.createByUser')" for="producto-createByUser"
              >Create By User</label
            >
            <input
              type="text"
              class="form-control"
              name="createByUser"
              id="producto-createByUser"
              data-cy="createByUser"
              :class="{ valid: !$v.producto.createByUser.$invalid, invalid: $v.producto.createByUser.$invalid }"
              v-model="$v.producto.createByUser.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('segmaflotApp.producto.createdOn')" for="producto-createdOn">Created On</label>
            <div class="d-flex">
              <input
                id="producto-createdOn"
                data-cy="createdOn"
                type="datetime-local"
                class="form-control"
                name="createdOn"
                :class="{ valid: !$v.producto.createdOn.$invalid, invalid: $v.producto.createdOn.$invalid }"
                :value="convertDateTimeFromServer($v.producto.createdOn.$model)"
                @change="updateInstantField('createdOn', $event)"
              />
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('segmaflotApp.producto.modifyByUser')" for="producto-modifyByUser"
              >Modify By User</label
            >
            <input
              type="text"
              class="form-control"
              name="modifyByUser"
              id="producto-modifyByUser"
              data-cy="modifyByUser"
              :class="{ valid: !$v.producto.modifyByUser.$invalid, invalid: $v.producto.modifyByUser.$invalid }"
              v-model="$v.producto.modifyByUser.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('segmaflotApp.producto.modifiedOn')" for="producto-modifiedOn">Modified On</label>
            <div class="d-flex">
              <input
                id="producto-modifiedOn"
                data-cy="modifiedOn"
                type="datetime-local"
                class="form-control"
                name="modifiedOn"
                :class="{ valid: !$v.producto.modifiedOn.$invalid, invalid: $v.producto.modifiedOn.$invalid }"
                :value="convertDateTimeFromServer($v.producto.modifiedOn.$model)"
                @change="updateInstantField('modifiedOn', $event)"
              />
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('segmaflotApp.producto.auditStatus')" for="producto-auditStatus"
              >Audit Status</label
            >
            <input
              type="text"
              class="form-control"
              name="auditStatus"
              id="producto-auditStatus"
              data-cy="auditStatus"
              :class="{ valid: !$v.producto.auditStatus.$invalid, invalid: $v.producto.auditStatus.$invalid }"
              v-model="$v.producto.auditStatus.$model"
            />
          </div>
        </div>
        <div>
          <button type="button" id="cancel-save" data-cy="entityCreateCancelButton" class="btn btn-secondary" v-on:click="previousState()">
            <font-awesome-icon icon="ban"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.cancel')">Cancel</span>
          </button>
          <button
            type="submit"
            id="save-entity"
            data-cy="entityCreateSaveButton"
            :disabled="$v.producto.$invalid || isSaving"
            class="btn btn-primary"
          >
            <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
          </button>
        </div>
      </form>
    </div>
  </div>
</template>
<script lang="ts" src="./producto-update.component.ts"></script>
