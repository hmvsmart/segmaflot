<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="resurtir">
        <h2 class="jh-entity-heading" data-cy="resurtirDetailsHeading">
          <span v-text="$t('segmaflotApp.resurtir.detail.title')">Resurtir</span> {{ resurtir.id }}
        </h2>
        <dl class="row jh-entity-details">
          <dt>
            <span v-text="$t('segmaflotApp.resurtir.fecha')">Fecha</span>
          </dt>
          <dd>
            <span v-if="resurtir.fecha">{{ $d(Date.parse(resurtir.fecha), 'long') }}</span>
          </dd>
          <dt>
            <span v-text="$t('segmaflotApp.resurtir.total')">Total</span>
          </dt>
          <dd>
            <span>{{ resurtir.total }}</span>
          </dd>
          <dt>
            <span v-text="$t('segmaflotApp.resurtir.createByUser')">Create By User</span>
          </dt>
          <dd>
            <span>{{ resurtir.createByUser }}</span>
          </dd>
          <dt>
            <span v-text="$t('segmaflotApp.resurtir.createdOn')">Created On</span>
          </dt>
          <dd>
            <span v-if="resurtir.createdOn">{{ $d(Date.parse(resurtir.createdOn), 'long') }}</span>
          </dd>
          <dt>
            <span v-text="$t('segmaflotApp.resurtir.modifyByUser')">Modify By User</span>
          </dt>
          <dd>
            <span>{{ resurtir.modifyByUser }}</span>
          </dd>
          <dt>
            <span v-text="$t('segmaflotApp.resurtir.modifiedOn')">Modified On</span>
          </dt>
          <dd>
            <span v-if="resurtir.modifiedOn">{{ $d(Date.parse(resurtir.modifiedOn), 'long') }}</span>
          </dd>
          <dt>
            <span v-text="$t('segmaflotApp.resurtir.auditStatus')">Audit Status</span>
          </dt>
          <dd>
            <span>{{ resurtir.auditStatus }}</span>
          </dd>
          <dt>
            <span v-text="$t('segmaflotApp.resurtir.inventario')">Inventario</span>
          </dt>
          <dd>
            <div v-if="resurtir.inventario">
              <router-link :to="{ name: 'InventarioView', params: { inventarioId: resurtir.inventario.id } }">{{
                resurtir.inventario.id
              }}</router-link>
            </div>
          </dd>
        </dl>
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
        </button>
        <router-link v-if="resurtir.id" :to="{ name: 'ResurtirEdit', params: { resurtirId: resurtir.id } }" custom v-slot="{ navigate }">
          <button @click="navigate" class="btn btn-primary">
            <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.edit')"> Edit</span>
          </button>
        </router-link>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./resurtir-details.component.ts"></script>
