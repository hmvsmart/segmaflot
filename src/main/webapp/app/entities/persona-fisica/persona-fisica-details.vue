<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="personaFisica">
        <h2 class="jh-entity-heading" data-cy="personaFisicaDetailsHeading">
          <span v-text="$t('segmaflotApp.personaFisica.detail.title')">PersonaFisica</span> {{ personaFisica.id }}
        </h2>
        <dl class="row jh-entity-details">
          <dt>
            <span v-text="$t('segmaflotApp.personaFisica.nombre')">Nombre</span>
          </dt>
          <dd>
            <span>{{ personaFisica.nombre }}</span>
          </dd>
          <dt>
            <span v-text="$t('segmaflotApp.personaFisica.apaterno')">Apaterno</span>
          </dt>
          <dd>
            <span>{{ personaFisica.apaterno }}</span>
          </dd>
          <dt>
            <span v-text="$t('segmaflotApp.personaFisica.amaterno')">Amaterno</span>
          </dt>
          <dd>
            <span>{{ personaFisica.amaterno }}</span>
          </dd>
          <dt>
            <span v-text="$t('segmaflotApp.personaFisica.fechaNacimiento')">Fecha Nacimiento</span>
          </dt>
          <dd>
            <span>{{ personaFisica.fechaNacimiento }}</span>
          </dd>
          <dt>
            <span v-text="$t('segmaflotApp.personaFisica.curp')">Curp</span>
          </dt>
          <dd>
            <span>{{ personaFisica.curp }}</span>
          </dd>
          <dt>
            <span v-text="$t('segmaflotApp.personaFisica.createByUser')">Create By User</span>
          </dt>
          <dd>
            <span>{{ personaFisica.createByUser }}</span>
          </dd>
          <dt>
            <span v-text="$t('segmaflotApp.personaFisica.createdOn')">Created On</span>
          </dt>
          <dd>
            <span v-if="personaFisica.createdOn">{{ $d(Date.parse(personaFisica.createdOn), 'long') }}</span>
          </dd>
          <dt>
            <span v-text="$t('segmaflotApp.personaFisica.modifyByUser')">Modify By User</span>
          </dt>
          <dd>
            <span>{{ personaFisica.modifyByUser }}</span>
          </dd>
          <dt>
            <span v-text="$t('segmaflotApp.personaFisica.modifiedOn')">Modified On</span>
          </dt>
          <dd>
            <span v-if="personaFisica.modifiedOn">{{ $d(Date.parse(personaFisica.modifiedOn), 'long') }}</span>
          </dd>
          <dt>
            <span v-text="$t('segmaflotApp.personaFisica.auditStatus')">Audit Status</span>
          </dt>
          <dd>
            <span>{{ personaFisica.auditStatus }}</span>
          </dd>
          <dt>
            <span v-text="$t('segmaflotApp.personaFisica.persona')">Persona</span>
          </dt>
          <dd>
            <div v-if="personaFisica.persona">
              <router-link :to="{ name: 'PersonaView', params: { personaId: personaFisica.persona.id } }">{{
                personaFisica.persona.id
              }}</router-link>
            </div>
          </dd>
        </dl>
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
        </button>
        <router-link
          v-if="personaFisica.id"
          :to="{ name: 'PersonaFisicaEdit', params: { personaFisicaId: personaFisica.id } }"
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

<script lang="ts" src="./persona-fisica-details.component.ts"></script>
