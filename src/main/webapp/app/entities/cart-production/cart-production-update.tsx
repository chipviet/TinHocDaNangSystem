import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
import { Translate, translate, ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { ICart } from 'app/shared/model/cart.model';
import { getEntities as getCarts } from 'app/entities/cart/cart.reducer';
import { IProduction } from 'app/shared/model/production.model';
import { getEntities as getProductions } from 'app/entities/production/production.reducer';
import { getEntity, updateEntity, createEntity, reset } from './cart-production.reducer';
import { ICartProduction } from 'app/shared/model/cart-production.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface ICartProductionUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const CartProductionUpdate = (props: ICartProductionUpdateProps) => {
  const [cartId, setCartId] = useState('0');
  const [produtionId, setProdutionId] = useState('0');
  const [isNew, setIsNew] = useState(!props.match.params || !props.match.params.id);

  const { cartProductionEntity, carts, productions, loading, updating } = props;

  const handleClose = () => {
    props.history.push('/cart-production' + props.location.search);
  };

  useEffect(() => {
    if (isNew) {
      props.reset();
    } else {
      props.getEntity(props.match.params.id);
    }

    props.getCarts();
    props.getProductions();
  }, []);

  useEffect(() => {
    if (props.updateSuccess) {
      handleClose();
    }
  }, [props.updateSuccess]);

  const saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      const entity = {
        ...cartProductionEntity,
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
          <h2 id="tinhocdanangApp.cartProduction.home.createOrEditLabel">
            <Translate contentKey="tinhocdanangApp.cartProduction.home.createOrEditLabel">Create or edit a CartProduction</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <AvForm model={isNew ? {} : cartProductionEntity} onSubmit={saveEntity}>
              {!isNew ? (
                <AvGroup>
                  <Label for="cart-production-id">
                    <Translate contentKey="global.field.id">ID</Translate>
                  </Label>
                  <AvInput id="cart-production-id" type="text" className="form-control" name="id" required readOnly />
                </AvGroup>
              ) : null}
              <AvGroup>
                <Label id="creationDateLabel" for="cart-production-creationDate">
                  <Translate contentKey="tinhocdanangApp.cartProduction.creationDate">Creation Date</Translate>
                </Label>
                <AvField id="cart-production-creationDate" type="date" className="form-control" name="creationDate" />
              </AvGroup>
              <AvGroup>
                <Label id="quanlityLabel" for="cart-production-quanlity">
                  <Translate contentKey="tinhocdanangApp.cartProduction.quanlity">Quanlity</Translate>
                </Label>
                <AvField id="cart-production-quanlity" type="string" className="form-control" name="quanlity" />
              </AvGroup>
              <AvGroup>
                <Label for="cart-production-cart">
                  <Translate contentKey="tinhocdanangApp.cartProduction.cart">Cart</Translate>
                </Label>
                <AvInput
                  id="cart-production-cart"
                  type="select"
                  className="form-control"
                  name="cart.id"
                  value={isNew ? carts[0] && carts[0].id : cartProductionEntity.cart?.id}
                  required
                >
                  {carts
                    ? carts.map(otherEntity => (
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
                <Label for="cart-production-prodution">
                  <Translate contentKey="tinhocdanangApp.cartProduction.prodution">Prodution</Translate>
                </Label>
                <AvInput
                  id="cart-production-prodution"
                  type="select"
                  className="form-control"
                  name="prodution.id"
                  value={isNew ? productions[0] && productions[0].id : cartProductionEntity.prodution?.id}
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
              <Button tag={Link} id="cancel-save" to="/cart-production" replace color="info">
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
  carts: storeState.cart.entities,
  productions: storeState.production.entities,
  cartProductionEntity: storeState.cartProduction.entity,
  loading: storeState.cartProduction.loading,
  updating: storeState.cartProduction.updating,
  updateSuccess: storeState.cartProduction.updateSuccess,
});

const mapDispatchToProps = {
  getCarts,
  getProductions,
  getEntity,
  updateEntity,
  createEntity,
  reset,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(CartProductionUpdate);
