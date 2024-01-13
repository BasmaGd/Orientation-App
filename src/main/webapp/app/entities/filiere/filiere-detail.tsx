import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './filiere.reducer';

export const FiliereDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const filiereEntity = useAppSelector(state => state.filiere.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="filiereDetailsHeading">
          <Translate contentKey="gestionDesEtudiantsApp.filiere.detail.title">Filiere</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{filiereEntity.id}</dd>
          <dt>
            <span id="nomFiliere">
              <Translate contentKey="gestionDesEtudiantsApp.filiere.nomFiliere">Nom Filiere</Translate>
            </span>
          </dt>
          <dd>{filiereEntity.nomFiliere}</dd>
          <dt>
            <span id="description">
              <Translate contentKey="gestionDesEtudiantsApp.filiere.description">Description</Translate>
            </span>
          </dt>
          <dd>{filiereEntity.description}</dd>
          <dt>
            <Translate contentKey="gestionDesEtudiantsApp.filiere.nomCours">Nom Cours</Translate>
          </dt>
          <dd>
            {filiereEntity.nomCours
              ? filiereEntity.nomCours.map((val, i) => (
                  <span key={val.id}>
                    <a>{val.id}</a>
                    {filiereEntity.nomCours && i === filiereEntity.nomCours.length - 1 ? '' : ', '}
                  </span>
                ))
              : null}
          </dd>
        </dl>
        <Button tag={Link} to="/filiere" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/filiere/${filiereEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default FiliereDetail;
