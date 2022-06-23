<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="direccionPersona">
        <h2 class="jh-entity-heading" data-cy="direccionPersonaDetailsHeading">
          <span v-text="$t('segmaflotApp.direccionPersona.detail.title')">DireccionPersona</span> {{ direccionPersona.id }}
        </h2>
        <dl class="row jh-entity-details">
          <dt>
            <span v-text="$t('segmaflotApp.direccionPersona.fecha')">Fecha</span>
          </dt>
          <dd>
            <span>{{ direccionPersona.fecha }}</span>
          </dd>
          <dt>
            <span v-text="$t('segmaflotApp.direccionPersona.status')">Status</span>
          </dt>
          <dd>
            <span>{{ direccionPersona.status }}</span>
          </dd>
          <dt>
            <span v-text="$t('segmaflotApp.direccionPersona.createByUser')">Create By User</span>
          </dt>
          <dd>
            <span>{{ direccionPersona.createByUser }}</span>
          </dd>
          <dt>
            <span v-text="$t('segmaflotApp.direccionPersona.createdOn')">Created On</span>
          </dt>
          <dd>
            <span v-if="direccionPersona.createdOn">{{ $d(Date.parse(direccionPersona.createdOn), 'long') }}</span>
          </dd>
          <dt>
            <span v-text="$t('segmaflotApp.direccionPersona.modifyByUser')">Modify By User</span>
          </dt>
          <dd>
            <span>{{ direccionPersona.modifyByUser }}</span>
          </dd>
          <dt>
            <span v-text="$t('segmaflotApp.direccionPersona.modifiedOn')">Modified On</span>
          </dt>
          <dd>
            <span v-if="direccionPersona.modifiedOn">{{ $d(Date.parse(direccionPersona.modifiedOn), 'long') }}</span>
          </dd>
          <dt>
            <span v-text="$t('segmaflotApp.direccionPersona.auditStatus')">Audit Status</span>
          </dt>
          <dd>
            <span>{{ direccionPersona.auditStatus }}</span>
          </dd>
          <dt>
            <span v-text="$t('segmaflotApp.direccionPersona.direccion')">Direccion</span>
          </dt>
          <dd>
            <div v-if="direccionPersona.direccion">
              <router-link :to="{ name: 'DireccionView', params: { direccionId: direccionPersona.direccion.id } }">{{
                direccionPersona.direccion.id
              }}</router-link>
            </div>
          </dd>
          <dt>
            <span v-text="$t('segmaflotApp.direccionPersona.persona')">Persona</span>
          </dt>
          <dd>
            <div v-if="direccionPersona.persona">
              <router-link :to="{ name: 'PersonaView', params: { personaId: direccionPersona.persona.id } }">{{
                direccionPersona.persona.id
              }}</router-link>
            </div>
          </dd>
        </dl>
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
        </button>
        <router-link
          v-if="direccionPersona.id"
          :to="{ name: 'DireccionPersonaEdit', params: { direccionPersonaId: direccionPersona.id } }"
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

<script lang="ts" src="./direccion-persona-details.component.ts"></script>
