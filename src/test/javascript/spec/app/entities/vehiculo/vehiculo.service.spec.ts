/* tslint:disable max-line-length */
import axios from 'axios';
import sinon from 'sinon';
import dayjs from 'dayjs';

import { DATE_TIME_FORMAT } from '@/shared/date/filters';
import VehiculoService from '@/entities/vehiculo/vehiculo.service';
import { Vehiculo } from '@/shared/model/vehiculo.model';

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
  describe('Vehiculo Service', () => {
    let service: VehiculoService;
    let elemDefault;
    let currentDate: Date;

    beforeEach(() => {
      service = new VehiculoService();
      currentDate = new Date();
      elemDefault = new Vehiculo(
        123,
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        currentDate,
        'AAAAAAA',
        currentDate,
        'AAAAAAA'
      );
    });

    describe('Service methods', () => {
      it('should find an element', async () => {
        const returnedFromService = Object.assign(
          {
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

      it('should create a Vehiculo', async () => {
        const returnedFromService = Object.assign(
          {
            id: 123,
            createdOn: dayjs(currentDate).format(DATE_TIME_FORMAT),
            modifiedOn: dayjs(currentDate).format(DATE_TIME_FORMAT),
          },
          elemDefault
        );
        const expected = Object.assign(
          {
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

      it('should not create a Vehiculo', async () => {
        axiosStub.post.rejects(error);

        return service
          .create({})
          .then()
          .catch(err => {
            expect(err).toMatchObject(error);
          });
      });

      it('should update a Vehiculo', async () => {
        const returnedFromService = Object.assign(
          {
            marca: 'BBBBBB',
            submarca: 'BBBBBB',
            modelo: 'BBBBBB',
            generacion: 'BBBBBB',
            tipoVehiculo: 'BBBBBB',
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

      it('should not update a Vehiculo', async () => {
        axiosStub.put.rejects(error);

        return service
          .update({})
          .then()
          .catch(err => {
            expect(err).toMatchObject(error);
          });
      });

      it('should partial update a Vehiculo', async () => {
        const patchObject = Object.assign(
          {
            submarca: 'BBBBBB',
            tipoVehiculo: 'BBBBBB',
            createByUser: 'BBBBBB',
            modifyByUser: 'BBBBBB',
            auditStatus: 'BBBBBB',
          },
          new Vehiculo()
        );
        const returnedFromService = Object.assign(patchObject, elemDefault);

        const expected = Object.assign(
          {
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

      it('should not partial update a Vehiculo', async () => {
        axiosStub.patch.rejects(error);

        return service
          .partialUpdate({})
          .then()
          .catch(err => {
            expect(err).toMatchObject(error);
          });
      });

      it('should return a list of Vehiculo', async () => {
        const returnedFromService = Object.assign(
          {
            marca: 'BBBBBB',
            submarca: 'BBBBBB',
            modelo: 'BBBBBB',
            generacion: 'BBBBBB',
            tipoVehiculo: 'BBBBBB',
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

      it('should not return a list of Vehiculo', async () => {
        axiosStub.get.rejects(error);

        return service
          .retrieve()
          .then()
          .catch(err => {
            expect(err).toMatchObject(error);
          });
      });

      it('should delete a Vehiculo', async () => {
        axiosStub.delete.resolves({ ok: true });
        return service.delete(123).then(res => {
          expect(res.ok).toBeTruthy();
        });
      });

      it('should not delete a Vehiculo', async () => {
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
