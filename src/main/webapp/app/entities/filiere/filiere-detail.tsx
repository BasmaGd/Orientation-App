import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, openFile, byteSize } from 'react-jhipster';
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
            <span id="imageFiliere">
              <Translate contentKey="gestionDesEtudiantsApp.filiere.imageFiliere">Image Filiere</Translate>
            </span>
          </dt>
          <dd>
            {filiereEntity.imageFiliere ? (
              <div>
                {filiereEntity.imageFiliereContentType ? (
                  <a onClick={openFile(filiereEntity.imageFiliereContentType, filiereEntity.imageFiliere)}>
                    <Translate contentKey="entity.action.open">Open</Translate>&nbsp;
                  </a>
                ) : null}
                <span>
                  {filiereEntity.imageFiliereContentType}, {byteSize(filiereEntity.imageFiliere)}
                </span>
              </div>
            ) : null}
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
