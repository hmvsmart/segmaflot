<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="contacto">
        <h2 class="jh-entity-heading" data-cy="contactoDetailsHeading">
          <span v-text="$t('segmaflotApp.contacto.detail.title')">Contacto</span> {{ contacto.id }}
        </h2>
        <dl class="row jh-entity-details">
          <dt>
            <span v-text="$t('segmaflotApp.contacto.valor')">Valor</span>
          </dt>
          <dd>
            <span>{{ contacto.valor }}</span>
          </dd>
          <dt>
            <span v-text="$t('segmaflotApp.contacto.etiqueta')">Etiqueta</span>
          </dt>
          <dd>
            <span>{{ contacto.etiqueta }}</span>
          </dd>
          <dt>
            <span v-text="$t('segmaflotApp.contacto.string')">String</span>
          </dt>
          <dd>
            <span>{{ contacto.string }}</span>
          </dd>
          <dt>
            <span v-text="$t('segmaflotApp.contacto.createByUser')">Create By User</span>
          </dt>
          <dd>
            <span>{{ contacto.createByUser }}</span>
          </dd>
          <dt>
            <span v-text="$t('segmaflotApp.contacto.createdOn')">Created On</span>
          </dt>
          <dd>
            <span v-if="contacto.createdOn">{{ $d(Date.parse(contacto.createdOn), 'long') }}</span>
          </dd>
          <dt>
            <span v-text="$t('segmaflotApp.contacto.modifyByUser')">Modify By User</span>
          </dt>
          <dd>
            <span>{{ contacto.modifyByUser }}</span>
          </dd>
          <dt>
            <span v-text="$t('segmaflotApp.contacto.modifiedOn')">Modified On</span>
          </dt>
          <dd>
            <span v-if="contacto.modifiedOn">{{ $d(Date.parse(contacto.modifiedOn), 'long') }}</span>
          </dd>
          <dt>
            <span v-text="$t('segmaflotApp.contacto.auditStatus')">Audit Status</span>
          </dt>
          <dd>
            <span>{{ contacto.auditStatus }}</span>
          </dd>
          <dt>
            <span v-text="$t('segmaflotApp.contacto.tipoContacto')">Tipo Contacto</span>
          </dt>
          <dd>
            <div v-if="contacto.tipoContacto">
              <router-link :to="{ name: 'TipoContactoView', params: { tipoContactoId: contacto.tipoContacto.id } }">{{
                contacto.tipoContacto.id
              }}</router-link>
            </div>
          </dd>
          <dt>
            <span v-text="$t('segmaflotApp.contacto.persona')">Persona</span>
          </dt>
          <dd>
            <div v-if="contacto.persona">
              <router-link :to="{ name: 'PersonaView', params: { personaId: contacto.persona.id } }">{{ contacto.persona.id }}</router-link>
            </div>
          </dd>
        </dl>
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
        </button>
        <router-link v-if="contacto.id" :to="{ name: 'ContactoEdit', params: { contactoId: contacto.id } }" custom v-slot="{ navigate }">
          <button @click="navigate" class="btn btn-primary">
            <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.edit')"> Edit</span>
          </button>
        </router-link>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./contacto-details.component.ts"></script>
