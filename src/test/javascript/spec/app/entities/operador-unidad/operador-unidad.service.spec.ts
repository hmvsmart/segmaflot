/* tslint:disable max-line-length */
import axios from 'axios';
import sinon from 'sinon';
import dayjs from 'dayjs';

import { DATE_TIME_FORMAT } from '@/shared/date/filters';
import OperadorUnidadService from '@/entities/operador-unidad/operador-unidad.service';
import { OperadorUnidad } from '@/shared/model/operador-unidad.model';

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
  describe('OperadorUnidad Service', () => {
    let service: OperadorUnidadService;
    let elemDefault;
    let currentDate: Date;

    beforeEach(() => {
      service = new OperadorUnidadService();
      currentDate = new Date();
      elemDefault = new OperadorUnidad(123, currentDate, 'AAAAAAA', false, 'AAAAAAA', currentDate, 'AAAAAAA', currentDate, 'AAAAAAA');
    });

    describe('Service methods', () => {
      it('should find an element', async () => {
        const returnedFromService = Object.assign(
          {
            fecha: dayjs(currentDate).format(DATE_TIME_FORMAT),
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

      it('should create a OperadorUnidad', async () => {
        const returnedFromService = Object.assign(
          {
            id: 123,
            fecha: dayjs(currentDate).format(DATE_TIME_FORMAT),
            createdOn: dayjs(currentDate).format(DATE_TIME_FORMAT),
            modifiedOn: dayjs(currentDate).format(DATE_TIME_FORMAT),
          },
          elemDefault
        );
        const expected = Object.assign(
          {
            fecha: currentDate,
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

      it('should not create a OperadorUnidad', async () => {
        axiosStub.post.rejects(error);

        return service
          .create({})
          .then()
          .catch(err => {
            expect(err).toMatchObject(error);
          });
      });

      it('should update a OperadorUnidad', async () => {
        const returnedFromService = Object.assign(
          {
            fecha: dayjs(currentDate).format(DATE_TIME_FORMAT),
            rol: 'BBBBBB',
            status: true,
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
            fecha: currentDate,
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

      it('should not update a OperadorUnidad', async () => {
        axiosStub.put.rejects(error);

        return service
          .update({})
          .then()
          .catch(err => {
            expect(err).toMatchObject(error);
          });
      });

      it('should partial update a OperadorUnidad', async () => {
        const patchObject = Object.assign(
          {
            rol: 'BBBBBB',
            status: true,
            createdOn: dayjs(currentDate).format(DATE_TIME_FORMAT),
            modifyByUser: 'BBBBBB',
            modifiedOn: dayjs(currentDate).format(DATE_TIME_FORMAT),
            auditStatus: 'BBBBBB',
          },
          new OperadorUnidad()
        );
        const returnedFromService = Object.assign(patchObject, elemDefault);

        const expected = Object.assign(
          {
            fecha: currentDate,
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

      it('should not partial update a OperadorUnidad', async () => {
        axiosStub.patch.rejects(error);

        return service
          .partialUpdate({})
          .then()
          .catch(err => {
            expect(err).toMatchObject(error);
          });
      });

      it('should return a list of OperadorUnidad', async () => {
        const returnedFromService = Object.assign(
          {
            fecha: dayjs(currentDate).format(DATE_TIME_FORMAT),
            rol: 'BBBBBB',
            status: true,
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
            fecha: currentDate,
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

      it('should not return a list of OperadorUnidad', async () => {
        axiosStub.get.rejects(error);

        return service
          .retrieve()
          .then()
          .catch(err => {
            expect(err).toMatchObject(error);
          });
      });

      it('should delete a OperadorUnidad', async () => {
        axiosStub.delete.resolves({ ok: true });
        return service.delete(123).then(res => {
          expect(res.ok).toBeTruthy();
        });
      });

      it('should not delete a OperadorUnidad', async () => {
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
