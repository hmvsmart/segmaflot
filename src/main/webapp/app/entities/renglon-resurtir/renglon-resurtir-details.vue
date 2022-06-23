<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="renglonResurtir">
        <h2 class="jh-entity-heading" data-cy="renglonResurtirDetailsHeading">
          <span v-text="$t('segmaflotApp.renglonResurtir.detail.title')">RenglonResurtir</span> {{ renglonResurtir.id }}
        </h2>
        <dl class="row jh-entity-details">
          <dt>
            <span v-text="$t('segmaflotApp.renglonResurtir.fechaCaducidad')">Fecha Caducidad</span>
          </dt>
          <dd>
            <span>{{ renglonResurtir.fechaCaducidad }}</span>
          </dd>
          <dt>
            <span v-text="$t('segmaflotApp.renglonResurtir.cantidad')">Cantidad</span>
          </dt>
          <dd>
            <span>{{ renglonResurtir.cantidad }}</span>
          </dd>
          <dt>
            <span v-text="$t('segmaflotApp.renglonResurtir.precioCompra')">Precio Compra</span>
          </dt>
          <dd>
            <span>{{ renglonResurtir.precioCompra }}</span>
          </dd>
          <dt>
            <span v-text="$t('segmaflotApp.renglonResurtir.createByUser')">Create By User</span>
          </dt>
          <dd>
            <span>{{ renglonResurtir.createByUser }}</span>
          </dd>
          <dt>
            <span v-text="$t('segmaflotApp.renglonResurtir.createdOn')">Created On</span>
          </dt>
          <dd>
            <span v-if="renglonResurtir.createdOn">{{ $d(Date.parse(renglonResurtir.createdOn), 'long') }}</span>
          </dd>
          <dt>
            <span v-text="$t('segmaflotApp.renglonResurtir.modifyByUser')">Modify By User</span>
          </dt>
          <dd>
            <span>{{ renglonResurtir.modifyByUser }}</span>
          </dd>
          <dt>
            <span v-text="$t('segmaflotApp.renglonResurtir.modifiedOn')">Modified On</span>
          </dt>
          <dd>
            <span v-if="renglonResurtir.modifiedOn">{{ $d(Date.parse(renglonResurtir.modifiedOn), 'long') }}</span>
          </dd>
          <dt>
            <span v-text="$t('segmaflotApp.renglonResurtir.auditStatus')">Audit Status</span>
          </dt>
          <dd>
            <span>{{ renglonResurtir.auditStatus }}</span>
          </dd>
          <dt>
            <span v-text="$t('segmaflotApp.renglonResurtir.resurtir')">Resurtir</span>
          </dt>
          <dd>
            <div v-if="renglonResurtir.resurtir">
              <router-link :to="{ name: 'ResurtirView', params: { resurtirId: renglonResurtir.resurtir.id } }">{{
                renglonResurtir.resurtir.id
              }}</router-link>
            </div>
          </dd>
        </dl>
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
        </button>
        <router-link
          v-if="renglonResurtir.id"
          :to="{ name: 'RenglonResurtirEdit', params: { renglonResurtirId: renglonResurtir.id } }"
          custom
          v-slot="{ navigate }"
        >
          <button @click="navigate" class="btn btn-primary">
            <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.edit')"> Edit</span>
          </button>
        </router-link>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./renglon-resurtir-details.component.ts"></script>
