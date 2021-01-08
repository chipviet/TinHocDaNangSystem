import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput } from 'availity-reactstrap-validation';
import { Translate, translate, ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { IProduction } from 'app/shared/model/production.model';
import { getEntities as getProductions } from 'app/entities/production/production.reducer';
import { IGuarantee } from 'app/shared/model/guarantee.model';
import { getEntities as getGuarantees } from 'app/entities/guarantee/guarantee.reducer';
import { getEntity, updateEntity, createEntity, reset } from './guarantee-production.reducer';
import { IGuaranteeProduction } from 'app/shared/model/guarantee-production.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IGuaranteeProductionUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const GuaranteeProductionUpdate = (props: IGuaranteeProductionUpdateProps) => {
  const [productionId, setProductionId] = useState('0');
  const [guaranteeId, setGuaranteeId] = useState('0');
  const [isNew, setIsNew] = useState(!props.match.params || !props.match.params.id);

  const { guaranteeProductionEntity, productions, guarantees, loading, updating } = props;

  const handleClose = () => {
    props.history.push('/guarantee-production' + props.location.search);
  };

  useEffect(() => {
    if (isNew) {
      props.reset();
    } else {
      props.getEntity(props.match.params.id);
    }

    props.getProductions();
    props.getGuarantees();
  }, []);

  useEffect(() => {
    if (props.updateSuccess) {
      handleClose();
    }
  }, [props.updateSuccess]);

  const saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      const entity = {
        ...guaranteeProductionEntity,
        ...values,
      };

      if (isNew) {
        props.createEntity(entity);
      } else {
        props.updateEntity(entity);
      }
    }
  };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="tinhocdanangApp.guaranteeProduction.home.createOrEditLabel">
            <Translate contentKey="tinhocdanangApp.guaranteeProduction.home.createOrEditLabel">
              Create or edit a GuaranteeProduction
            </Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <AvForm model={isNew ? {} : guaranteeProductionEntity} onSubmit={saveEntity}>
              {!isNew ? (
                <AvGroup>
                  <Label for="guarantee-production-id">
                    <Translate contentKey="global.field.id">ID</Translate>
                  </Label>
                  <AvInput id="guarantee-production-id" type="text" className="form-control" name="id" required readOnly />
                </AvGroup>
              ) : null}
              <AvGroup>
                <Label for="guarantee-production-production">
                  <Translate contentKey="tinhocdanangApp.guaranteeProduction.production">Production</Translate>
                </Label>
                <AvInput
                  id="guarantee-production-production"
                  type="select"
                  className="form-control"
                  name="production.id"
                  value={isNew ? productions[0] && productions[0].id : guaranteeProductionEntity.production?.id}
                  required
                >
                  {productions
                    ? productions.map(otherEntity => (
                        <option value={otherEntity.id} key={otherEntity.id}>
                          {otherEntity.id}
                        </option>
                      ))
                    : null}
                </AvInput>
                <AvFeedback>
                  <Translate contentKey="entity.validation.required">This field is required.</Translate>
                </AvFeedback>
              </AvGroup>
              <AvGroup>
                <Label for="guarantee-production-guarantee">
                  <Translate contentKey="tinhocdanangApp.guaranteeProduction.guarantee">Guarantee</Translate>
                </Label>
                <AvInput
                  id="guarantee-production-guarantee"
                  type="select"
                  className="form-control"
                  name="guarantee.id"
                  value={isNew ? guarantees[0] && guarantees[0].id : guaranteeProductionEntity.guarantee?.id}
                  required
                >
                  {guarantees
                    ? guarantees.map(otherEntity => (
                        <option value={otherEntity.id} key={otherEntity.id}>
                          {otherEntity.id}
                        </option>
                      ))
                    : null}
                </AvInput>
                <AvFeedback>
                  <Translate contentKey="entity.validation.required">This field is required.</Translate>
                </AvFeedback>
              </AvGroup>
              <Button tag={Link} id="cancel-save" to="/guarantee-production" replace color="info">
                <FontAwesomeIcon icon="arrow-left" />
                &nbsp;
                <span className="d-none d-md-inline">
                  <Translate contentKey="entity.action.back">Back</Translate>
                </span>
              </Button>
              &nbsp;
              <Button color="primary" id="save-entity" type="submit" disabled={updating}>
                <FontAwesomeIcon icon="save" />
                &nbsp;
                <Translate contentKey="entity.action.save">Save</Translate>
              </Button>
            </AvForm>
          )}
        </Col>
      </Row>
    </div>
  );
};

const mapStateToProps = (storeState: IRootState) => ({
  productions: storeState.production.entities,
  guarantees: storeState.guarantee.entities,
  guaranteeProductionEntity: storeState.guaranteeProduction.entity,
  loading: storeState.guaranteeProduction.loading,
  updating: storeState.guaranteeProduction.updating,
  updateSuccess: storeState.guaranteeProduction.updateSuccess,
});

const mapDispatchToProps = {
  getProductions,
  getGuarantees,
  getEntity,
  updateEntity,
  createEntity,
  reset,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(GuaranteeProductionUpdate);
