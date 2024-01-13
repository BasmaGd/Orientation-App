import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './carriere.reducer';

export const CarriereDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const carriereEntity = useAppSelector(state => state.carriere.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="carriereDetailsHeading">
          <Translate contentKey="gestionDesEtudiantsApp.carriere.detail.title">Carriere</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{carriereEntity.id}</dd>
          <dt>
            <span id="nomCarriere">
              <Translate contentKey="gestionDesEtudiantsApp.carriere.nomCarriere">Nom Carriere</Translate>
            </span>
          </dt>
          <dd>{carriereEntity.nomCarriere}</dd>
          <dt>
            <span id="description">
              <Translate contentKey="gestionDesEtudiantsApp.carriere.description">Description</Translate>
            </span>
          </dt>
          <dd>{carriereEntity.description}</dd>
          <dt>
            <Translate contentKey="gestionDesEtudiantsApp.carriere.filiere">Filiere</Translate>
          </dt>
          <dd>
            {carriereEntity.filieres
              ? carriereEntity.filieres.map((val, i) => (
                  <span key={val.id}>
                    <a>{val.id}</a>
                    {carriereEntity.filieres && i === carriereEntity.filieres.length - 1 ? '' : ', '}
                  </span>
                ))
              : null}
          </dd>
          <dt>
            <Translate contentKey="gestionDesEtudiantsApp.carriere.cours">Cours</Translate>
          </dt>
          <dd>
            {carriereEntity.cours
              ? carriereEntity.cours.map((val, i) => (
                  <span key={val.id}>
                    <a>{val.id}</a>
                    {carriereEntity.cours && i === carriereEntity.cours.length - 1 ? '' : ', '}
                  </span>
                ))
              : null}
          </dd>
        </dl>
        <Button tag={Link} to="/carriere" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/carriere/${carriereEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default CarriereDetail;
