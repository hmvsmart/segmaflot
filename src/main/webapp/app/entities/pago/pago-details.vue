<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="pago">
        <h2 class="jh-entity-heading" data-cy="pagoDetailsHeading">
          <span v-text="$t('segmaflotApp.pago.detail.title')">Pago</span> {{ pago.id }}
        </h2>
        <dl class="row jh-entity-details">
          <dt>
            <span v-text="$t('segmaflotApp.pago.fecha')">Fecha</span>
          </dt>
          <dd>
            <span>{{ pago.fecha }}</span>
          </dd>
          <dt>
            <span v-text="$t('segmaflotApp.pago.tipoPago')">Tipo Pago</span>
          </dt>
          <dd>
            <span>{{ pago.tipoPago }}</span>
          </dd>
          <dt>
            <span v-text="$t('segmaflotApp.pago.monto')">Monto</span>
          </dt>
          <dd>
            <span>{{ pago.monto }}</span>
          </dd>
          <dt>
            <span v-text="$t('segmaflotApp.pago.area')">Area</span>
          </dt>
          <dd>
            <span>{{ pago.area }}</span>
          </dd>
          <dt>
            <span v-text="$t('segmaflotApp.pago.identificador')">Identificador</span>
          </dt>
          <dd>
            <span>{{ pago.identificador }}</span>
          </dd>
          <dt>
            <span v-text="$t('segmaflotApp.pago.createByUser')">Create By User</span>
          </dt>
          <dd>
            <span>{{ pago.createByUser }}</span>
          </dd>
          <dt>
            <span v-text="$t('segmaflotApp.pago.createdOn')">Created On</span>
          </dt>
          <dd>
            <span v-if="pago.createdOn">{{ $d(Date.parse(pago.createdOn), 'long') }}</span>
          </dd>
          <dt>
            <span v-text="$t('segmaflotApp.pago.modifyByUser')">Modify By User</span>
          </dt>
          <dd>
            <span>{{ pago.modifyByUser }}</span>
          </dd>
          <dt>
            <span v-text="$t('segmaflotApp.pago.modifiedOn')">Modified On</span>
          </dt>
          <dd>
            <span v-if="pago.modifiedOn">{{ $d(Date.parse(pago.modifiedOn), 'long') }}</span>
          </dd>
          <dt>
            <span v-text="$t('segmaflotApp.pago.auditStatus')">Audit Status</span>
          </dt>
          <dd>
            <span>{{ pago.auditStatus }}</span>
          </dd>
        </dl>
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
        </button>
        <router-link v-if="pago.id" :to="{ name: 'PagoEdit', params: { pagoId: pago.id } }" custom v-slot="{ navigate }">
          <button @click="navigate" class="btn btn-primary">
            <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.edit')"> Edit</span>
          </button>
        </router-link>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./pago-details.component.ts"></script>
