<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="personaMoral">
        <h2 class="jh-entity-heading" data-cy="personaMoralDetailsHeading">
          <span v-text="$t('segmaflotApp.personaMoral.detail.title')">PersonaMoral</span> {{ personaMoral.id }}
        </h2>
        <dl class="row jh-entity-details">
          <dt>
            <span v-text="$t('segmaflotApp.personaMoral.razonSocial')">Razon Social</span>
          </dt>
          <dd>
            <span>{{ personaMoral.razonSocial }}</span>
          </dd>
          <dt>
            <span v-text="$t('segmaflotApp.personaMoral.rfc')">Rfc</span>
          </dt>
          <dd>
            <span>{{ personaMoral.rfc }}</span>
          </dd>
          <dt>
            <span v-text="$t('segmaflotApp.personaMoral.createByUser')">Create By User</span>
          </dt>
          <dd>
            <span>{{ personaMoral.createByUser }}</span>
          </dd>
          <dt>
            <span v-text="$t('segmaflotApp.personaMoral.createdOn')">Created On</span>
          </dt>
          <dd>
            <span v-if="personaMoral.createdOn">{{ $d(Date.parse(personaMoral.createdOn), 'long') }}</span>
          </dd>
          <dt>
            <span v-text="$t('segmaflotApp.personaMoral.modifyByUser')">Modify By User</span>
          </dt>
          <dd>
            <span>{{ personaMoral.modifyByUser }}</span>
          </dd>
          <dt>
            <span v-text="$t('segmaflotApp.personaMoral.modifiedOn')">Modified On</span>
          </dt>
          <dd>
            <span v-if="personaMoral.modifiedOn">{{ $d(Date.parse(personaMoral.modifiedOn), 'long') }}</span>
          </dd>
          <dt>
            <span v-text="$t('segmaflotApp.personaMoral.auditStatus')">Audit Status</span>
          </dt>
          <dd>
            <span>{{ personaMoral.auditStatus }}</span>
          </dd>
          <dt>
            <span v-text="$t('segmaflotApp.personaMoral.persona')">Persona</span>
          </dt>
          <dd>
            <div v-if="personaMoral.persona">
              <router-link :to="{ name: 'PersonaView', params: { personaId: personaMoral.persona.id } }">{{
                personaMoral.persona.id
              }}</router-link>
            </div>
          </dd>
        </dl>
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
        </button>
        <router-link
          v-if="personaMoral.id"
          :to="{ name: 'PersonaMoralEdit', params: { personaMoralId: personaMoral.id } }"
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

<script lang="ts" src="./persona-moral-details.component.ts"></script>
