/* tslint:disable max-line-length */
import axios from 'axios';
import sinon from 'sinon';
import dayjs from 'dayjs';

import { DATE_TIME_FORMAT } from '@/shared/date/filters';
import DiaHorarioService from '@/entities/dia-horario/dia-horario.service';
import { DiaHorario } from '@/shared/model/dia-horario.model';

const error = {
  response: {
    status: null,
    data: {
      type: null,
    },
  },
};

const axiosStub = {
  get: sinon.stub(axios, 'get'),
  post: sinon.stub(axios, 'post'),
  put: sinon.stub(axios, 'put'),
  patch: sinon.stub(axios, 'patch'),
  delete: sinon.stub(axios, 'delete'),
};

describe('Service Tests', () => {
  describe('DiaHorario Service', () => {
    let service: DiaHorarioService;
    let elemDefault;
    let currentDate: Date;

    beforeEach(() => {
      service = new DiaHorarioService();
      currentDate = new Date();
      elemDefault = new DiaHorario(123, 0, currentDate, currentDate, 'AAAAAAA', currentDate, 'AAAAAAA', currentDate, 'AAAAAAA');
    });

    describe('Service methods', () => {
      it('should find an element', async () => {
        const returnedFromService = Object.assign(
          {
            horaEntrada: dayjs(currentDate).format(DATE_TIME_FORMAT),
            horaSalida: dayjs(currentDate).format(DATE_TIME_FORMAT),
            createdOn: dayjs(currentDate).format(DATE_TIME_FORMAT),
            modifiedOn: dayjs(currentDate).format(DATE_TIME_FORMAT),
          },
          elemDefault
        );
        axiosStub.get.resolves({ data: returnedFromService });

        return service.find(123).then(res => {
          expect(res).toMatchObject(elemDefault);
        });
      });

      it('should not find an element', async () => {
        axiosStub.get.rejects(error);
        return service
          .find(123)
          .then()
          .catch(err => {
            expect(err).toMatchObject(error);
          });
      });

      it('should create a DiaHorario', async () => {
        const returnedFromService = Object.assign(
          {
            id: 123,
            horaEntrada: dayjs(currentDate).format(DATE_TIME_FORMAT),
            horaSalida: dayjs(currentDate).format(DATE_TIME_FORMAT),
            createdOn: dayjs(currentDate).format(DATE_TIME_FORMAT),
            modifiedOn: dayjs(currentDate).format(DATE_TIME_FORMAT),
          },
          elemDefault
        );
        const expected = Object.assign(
          {
            horaEntrada: currentDate,
            horaSalida: currentDate,
            createdOn: currentDate,
            modifiedOn: currentDate,
          },
          returnedFromService
        );

        axiosStub.post.resolves({ data: returnedFromService });
        return service.create({}).then(res => {
          expect(res).toMatchObject(expected);
        });
      });

      it('should not create a DiaHorario', async () => {
        axiosStub.post.rejects(error);

        return service
          .create({})
          .then()
          .catch(err => {
            expect(err).toMatchObject(error);
          });
      });

      it('should update a DiaHorario', async () => {
        const returnedFromService = Object.assign(
          {
            dia: 1,
            horaEntrada: dayjs(currentDate).format(DATE_TIME_FORMAT),
            horaSalida: dayjs(currentDate).format(DATE_TIME_FORMAT),
            createByUser: 'BBBBBB',
            createdOn: dayjs(currentDate).format(DATE_TIME_FORMAT),
            modifyByUser: 'BBBBBB',
            modifiedOn: dayjs(currentDate).format(DATE_TIME_FORMAT),
            auditStatus: 'BBBBBB',
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            horaEntrada: currentDate,
            horaSalida: currentDate,
            createdOn: currentDate,
            modifiedOn: currentDate,
          },
          returnedFromService
        );
        axiosStub.put.resolves({ data: returnedFromService });

        return service.update(expected).then(res => {
          expect(res).toMatchObject(expected);
        });
      });

      it('should not update a DiaHorario', async () => {
        axiosStub.put.rejects(error);

        return service
          .update({})
          .then()
          .catch(err => {
            expect(err).toMatchObject(error);
          });
      });

      it('should partial update a DiaHorario', async () => {
        const patchObject = Object.assign(
          {
            dia: 1,
            createByUser: 'BBBBBB',
            createdOn: dayjs(currentDate).format(DATE_TIME_FORMAT),
            modifiedOn: dayjs(currentDate).format(DATE_TIME_FORMAT),
          },
          new DiaHorario()
        );
        const returnedFromService = Object.assign(patchObject, elemDefault);

        const expected = Object.assign(
          {
            horaEntrada: currentDate,
            horaSalida: currentDate,
            createdOn: currentDate,
            modifiedOn: currentDate,
          },
          returnedFromService
        );
        axiosStub.patch.resolves({ data: returnedFromService });

        return service.partialUpdate(patchObject).then(res => {
          expect(res).toMatchObject(expected);
        });
      });

      it('should not partial update a DiaHorario', async () => {
        axiosStub.patch.rejects(error);

        return service
          .partialUpdate({})
          .then()
          .catch(err => {
            expect(err).toMatchObject(error);
          });
      });

      it('should return a list of DiaHorario', async () => {
        const returnedFromService = Object.assign(
          {
            dia: 1,
            horaEntrada: dayjs(currentDate).format(DATE_TIME_FORMAT),
            horaSalida: dayjs(currentDate).format(DATE_TIME_FORMAT),
            createByUser: 'BBBBBB',
            createdOn: dayjs(currentDate).format(DATE_TIME_FORMAT),
            modifyByUser: 'BBBBBB',
            modifiedOn: dayjs(currentDate).format(DATE_TIME_FORMAT),
            auditStatus: 'BBBBBB',
          },
          elemDefault
        );
        const expected = Object.assign(
          {
            horaEntrada: currentDate,
            horaSalida: currentDate,
            createdOn: currentDate,
            modifiedOn: currentDate,
          },
          returnedFromService
        );
        axiosStub.get.resolves([returnedFromService]);
        return service.retrieve().then(res => {
          expect(res).toContainEqual(expected);
        });
      });

      it('should not return a list of DiaHorario', async () => {
        axiosStub.get.rejects(error);

        return service
          .retrieve()
          .then()
          .catch(err => {
            expect(err).toMatchObject(error);
          });
      });

      it('should delete a DiaHorario', async () => {
        axiosStub.delete.resolves({ ok: true });
        return service.delete(123).then(res => {
          expect(res.ok).toBeTruthy();
        });
      });

      it('should not delete a DiaHorario', async () => {
        axiosStub.delete.rejects(error);

        return service
          .delete(123)
          .then()
          .catch(err => {
            expect(err).toMatchObject(error);
          });
      });
    });
  });
});
