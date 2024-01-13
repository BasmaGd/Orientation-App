import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IFiliere } from 'app/shared/model/filiere.model';
import { getEntities as getFilieres } from 'app/entities/filiere/filiere.reducer';
import { ICarriere } from 'app/shared/model/carriere.model';
import { getEntity, updateEntity, createEntity, reset } from './carriere.reducer';

export const CarriereUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const filieres = useAppSelector(state => state.filiere.entities);
  const carriereEntity = useAppSelector(state => state.carriere.entity);
  const loading = useAppSelector(state => state.carriere.loading);
  const updating = useAppSelector(state => state.carriere.updating);
  const updateSuccess = useAppSelector(state => state.carriere.updateSuccess);

  const handleClose = () => {
    navigate('/carriere');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getFilieres({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  // eslint-disable-next-line complexity
  const saveEntity = values => {
    if (values.id !== undefined && typeof values.id !== 'number') {
      values.id = Number(values.id);
    }

    const entity = {
      ...carriereEntity,
      ...values,
      nomFilieres: mapIdList(values.nomFilieres),
    };

    if (isNew) {
      dispatch(createEntity(entity));
    } else {
      dispatch(updateEntity(entity));
    }
  };

  const defaultValues = () =>
    isNew
      ? {}
      : {
          ...carriereEntity,
          nomFilieres: carriereEntity?.nomFilieres?.map(e => e.id.toString()),
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="gestionDesEtudiantsApp.carriere.home.createOrEditLabel" data-cy="CarriereCreateUpdateHeading">
            <Translate contentKey="gestionDesEtudiantsApp.carriere.home.createOrEditLabel">Create or edit a Carriere</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? (
                <ValidatedField
                  name="id"
                  required
                  readOnly
                  id="carriere-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('gestionDesEtudiantsApp.carriere.nomCarriere')}
                id="carriere-nomCarriere"
                name="nomCarriere"
                data-cy="nomCarriere"
                type="text"
              />
              <ValidatedField
                label={translate('gestionDesEtudiantsApp.carriere.description')}
                id="carriere-description"
                name="description"
                data-cy="description"
                type="text"
              />
              <ValidatedField
                label={translate('gestionDesEtudiantsApp.carriere.nomFiliere')}
                id="carriere-nomFiliere"
                data-cy="nomFiliere"
                type="select"
                multiple
                name="nomFilieres"
              >
                <option value="" key="0" />
                {filieres
                  ? filieres.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.nomFiliere}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/carriere" replace color="info">
                <FontAwesomeIcon icon="arrow-left" />
                &nbsp;
                <span className="d-none d-md-inline">
                  <Translate contentKey="entity.action.back">Back</Translate>
                </span>
              </Button>
              &nbsp;
              <Button color="primary" id="save-entity" data-cy="entityCreateSaveButton" type="submit" disabled={updating}>
                <FontAwesomeIcon icon="save" />
                &nbsp;
                <Translate contentKey="entity.action.save">Save</Translate>
              </Button>
            </ValidatedForm>
          )}
        </Col>
      </Row>
    </div>
  );
};

export default CarriereUpdate;
