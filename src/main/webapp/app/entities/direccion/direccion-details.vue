<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="direccion">
        <h2 class="jh-entity-heading" data-cy="direccionDetailsHeading">
          <span v-text="$t('segmaflotApp.direccion.detail.title')">Direccion</span> {{ direccion.id }}
        </h2>
        <dl class="row jh-entity-details">
          <dt>
            <span v-text="$t('segmaflotApp.direccion.calle')">Calle</span>
          </dt>
          <dd>
            <span>{{ direccion.calle }}</span>
          </dd>
          <dt>
            <span v-text="$t('segmaflotApp.direccion.numeroExterior')">Numero Exterior</span>
          </dt>
          <dd>
            <span>{{ direccion.numeroExterior }}</span>
          </dd>
          <dt>
            <span v-text="$t('segmaflotApp.direccion.numeroInterior')">Numero Interior</span>
          </dt>
          <dd>
            <span>{{ direccion.numeroInterior }}</span>
          </dd>
          <dt>
            <span v-text="$t('segmaflotApp.direccion.createByUser')">Create By User</span>
          </dt>
          <dd>
            <span>{{ direccion.createByUser }}</span>
          </dd>
          <dt>
            <span v-text="$t('segmaflotApp.direccion.createdOn')">Created On</span>
          </dt>
          <dd>
            <span v-if="direccion.createdOn">{{ $d(Date.parse(direccion.createdOn), 'long') }}</span>
          </dd>
          <dt>
            <span v-text="$t('segmaflotApp.direccion.modifyByUser')">Modify By User</span>
          </dt>
          <dd>
            <span>{{ direccion.modifyByUser }}</span>
          </dd>
          <dt>
            <span v-text="$t('segmaflotApp.direccion.modifiedOn')">Modified On</span>
          </dt>
          <dd>
            <span v-if="direccion.modifiedOn">{{ $d(Date.parse(direccion.modifiedOn), 'long') }}</span>
          </dd>
          <dt>
            <span v-text="$t('segmaflotApp.direccion.auditStatus')">Audit Status</span>
          </dt>
          <dd>
            <span>{{ direccion.auditStatus }}</span>
          </dd>
          <dt>
            <span v-text="$t('segmaflotApp.direccion.colonia')">Colonia</span>
          </dt>
          <dd>
            <div v-if="direccion.colonia">
              <router-link :to="{ name: 'ColoniaView', params: { coloniaId: direccion.colonia.id } }">{{
                direccion.colonia.id
              }}</router-link>
            </div>
          </dd>
        </dl>
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
        </button>
        <router-link
          v-if="direccion.id"
          :to="{ name: 'DireccionEdit', params: { direccionId: direccion.id } }"
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

<script lang="ts" src="./direccion-details.component.ts"></script>
