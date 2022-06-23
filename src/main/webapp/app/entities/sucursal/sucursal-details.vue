<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="sucursal">
        <h2 class="jh-entity-heading" data-cy="sucursalDetailsHeading">
          <span v-text="$t('segmaflotApp.sucursal.detail.title')">Sucursal</span> {{ sucursal.id }}
        </h2>
        <dl class="row jh-entity-details">
          <dt>
            <span v-text="$t('segmaflotApp.sucursal.nombre')">Nombre</span>
          </dt>
          <dd>
            <span>{{ sucursal.nombre }}</span>
          </dd>
          <dt>
            <span v-text="$t('segmaflotApp.sucursal.tipoSucursal')">Tipo Sucursal</span>
          </dt>
          <dd>
            <span>{{ sucursal.tipoSucursal }}</span>
          </dd>
          <dt>
            <span v-text="$t('segmaflotApp.sucursal.telefono')">Telefono</span>
          </dt>
          <dd>
            <span>{{ sucursal.telefono }}</span>
          </dd>
          <dt>
            <span v-text="$t('segmaflotApp.sucursal.status')">Status</span>
          </dt>
          <dd>
            <span>{{ sucursal.status }}</span>
          </dd>
          <dt>
            <span v-text="$t('segmaflotApp.sucursal.createByUser')">Create By User</span>
          </dt>
          <dd>
            <span>{{ sucursal.createByUser }}</span>
          </dd>
          <dt>
            <span v-text="$t('segmaflotApp.sucursal.createdOn')">Created On</span>
          </dt>
          <dd>
            <span v-if="sucursal.createdOn">{{ $d(Date.parse(sucursal.createdOn), 'long') }}</span>
          </dd>
          <dt>
            <span v-text="$t('segmaflotApp.sucursal.modifyByUser')">Modify By User</span>
          </dt>
          <dd>
            <span>{{ sucursal.modifyByUser }}</span>
          </dd>
          <dt>
            <span v-text="$t('segmaflotApp.sucursal.modifiedOn')">Modified On</span>
          </dt>
          <dd>
            <span v-if="sucursal.modifiedOn">{{ $d(Date.parse(sucursal.modifiedOn), 'long') }}</span>
          </dd>
          <dt>
            <span v-text="$t('segmaflotApp.sucursal.auditStatus')">Audit Status</span>
          </dt>
          <dd>
            <span>{{ sucursal.auditStatus }}</span>
          </dd>
          <dt>
            <span v-text="$t('segmaflotApp.sucursal.empresa')">Empresa</span>
          </dt>
          <dd>
            <div v-if="sucursal.empresa">
              <router-link :to="{ name: 'PersonaMoralView', params: { personaMoralId: sucursal.empresa.id } }">{{
                sucursal.empresa.id
              }}</router-link>
            </div>
          </dd>
        </dl>
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
        </button>
        <router-link v-if="sucursal.id" :to="{ name: 'SucursalEdit', params: { sucursalId: sucursal.id } }" custom v-slot="{ navigate }">
          <button @click="navigate" class="btn btn-primary">
            <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.edit')"> Edit</span>
          </button>
        </router-link>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./sucursal-details.component.ts"></script>
