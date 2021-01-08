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
import { IPromotion } from 'app/shared/model/promotion.model';
import { getEntities as getPromotions } from 'app/entities/promotion/promotion.reducer';
import { getEntity, updateEntity, createEntity, reset } from './promotion-production.reducer';
import { IPromotionProduction } from 'app/shared/model/promotion-production.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IPromotionProductionUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const PromotionProductionUpdate = (props: IPromotionProductionUpdateProps) => {
  const [productionId, setProductionId] = useState('0');
  const [promotionId, setPromotionId] = useState('0');
  const [isNew, setIsNew] = useState(!props.match.params || !props.match.params.id);

  const { promotionProductionEntity, productions, promotions, loading, updating } = props;

  const handleClose = () => {
    props.history.push('/promotion-production' + props.location.search);
  };

  useEffect(() => {
    if (isNew) {
      props.reset();
    } else {
      props.getEntity(props.match.params.id);
    }

    props.getProductions();
    props.getPromotions();
  }, []);

  useEffect(() => {
    if (props.updateSuccess) {
      handleClose();
    }
  }, [props.updateSuccess]);

  const saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      const entity = {
        ...promotionProductionEntity,
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
          <h2 id="tinhocdanangApp.promotionProduction.home.createOrEditLabel">
            <Translate contentKey="tinhocdanangApp.promotionProduction.home.createOrEditLabel">
              Create or edit a PromotionProduction
            </Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <AvForm model={isNew ? {} : promotionProductionEntity} onSubmit={saveEntity}>
              {!isNew ? (
                <AvGroup>
                  <Label for="promotion-production-id">
                    <Translate contentKey="global.field.id">ID</Translate>
                  </Label>
                  <AvInput id="promotion-production-id" type="text" className="form-control" name="id" required readOnly />
                </AvGroup>
              ) : null}
              <AvGroup>
                <Label for="promotion-production-production">
                  <Translate contentKey="tinhocdanangApp.promotionProduction.production">Production</Translate>
                </Label>
                <AvInput
                  id="promotion-production-production"
                  type="select"
                  className="form-control"
                  name="production.id"
                  value={isNew ? productions[0] && productions[0].id : promotionProductionEntity.production?.id}
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
                <Label for="promotion-production-promotion">
                  <Translate contentKey="tinhocdanangApp.promotionProduction.promotion">Promotion</Translate>
                </Label>
                <AvInput
                  id="promotion-production-promotion"
                  type="select"
                  className="form-control"
                  name="promotion.id"
                  value={isNew ? promotions[0] && promotions[0].id : promotionProductionEntity.promotion?.id}
                  required
                >
                  {promotions
                    ? promotions.map(otherEntity => (
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
              <Button tag={Link} id="cancel-save" to="/promotion-production" replace color="info">
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
  promotions: storeState.promotion.entities,
  promotionProductionEntity: storeState.promotionProduction.entity,
  loading: storeState.promotionProduction.loading,
  updating: storeState.promotionProduction.updating,
  updateSuccess: storeState.promotionProduction.updateSuccess,
});

const mapDispatchToProps = {
  getProductions,
  getPromotions,
  getEntity,
  updateEntity,
  createEntity,
  reset,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(PromotionProductionUpdate);
