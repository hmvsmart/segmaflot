<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="nivel">
        <h2 class="jh-entity-heading" data-cy="nivelDetailsHeading">
          <span v-text="$t('segmaflotApp.nivel.detail.title')">Nivel</span> {{ nivel.id }}
        </h2>
        <dl class="row jh-entity-details">
          <dt>
            <span v-text="$t('segmaflotApp.nivel.nombre')">Nombre</span>
          </dt>
          <dd>
            <span>{{ nivel.nombre }}</span>
          </dd>
          <dt>
            <span v-text="$t('segmaflotApp.nivel.createByUser')">Create By User</span>
          </dt>
          <dd>
            <span>{{ nivel.createByUser }}</span>
          </dd>
          <dt>
            <span v-text="$t('segmaflotApp.nivel.createdOn')">Created On</span>
          </dt>
          <dd>
            <span v-if="nivel.createdOn">{{ $d(Date.parse(nivel.createdOn), 'long') }}</span>
          </dd>
          <dt>
            <span v-text="$t('segmaflotApp.nivel.modifyByUser')">Modify By User</span>
          </dt>
          <dd>
            <span>{{ nivel.modifyByUser }}</span>
          </dd>
          <dt>
            <span v-text="$t('segmaflotApp.nivel.modifiedOn')">Modified On</span>
          </dt>
          <dd>
            <span v-if="nivel.modifiedOn">{{ $d(Date.parse(nivel.modifiedOn), 'long') }}</span>
          </dd>
          <dt>
            <span v-text="$t('segmaflotApp.nivel.auditStatus')">Audit Status</span>
          </dt>
          <dd>
            <span>{{ nivel.auditStatus }}</span>
          </dd>
          <dt>
            <span v-text="$t('segmaflotApp.nivel.estante')">Estante</span>
          </dt>
          <dd>
            <div v-if="nivel.estante">
              <router-link :to="{ name: 'EstanteView', params: { estanteId: nivel.estante.id } }">{{ nivel.estante.id }}</router-link>
            </div>
          </dd>
        </dl>
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
        </button>
        <router-link v-if="nivel.id" :to="{ name: 'NivelEdit', params: { nivelId: nivel.id } }" custom v-slot="{ navigate }">
          <button @click="navigate" class="btn btn-primary">
            <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.edit')"> Edit</span>
          </button>
        </router-link>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./nivel-details.component.ts"></script>
