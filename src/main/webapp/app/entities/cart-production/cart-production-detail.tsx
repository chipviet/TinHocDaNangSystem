import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, ICrudGetAction, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './cart-production.reducer';
import { ICartProduction } from 'app/shared/model/cart-production.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface ICartProductionDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const CartProductionDetail = (props: ICartProductionDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { cartProductionEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2>
          <Translate contentKey="tinhocdanangApp.cartProduction.detail.title">CartProduction</Translate> [<b>{cartProductionEntity.id}</b>]
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="creationDate">
              <Translate contentKey="tinhocdanangApp.cartProduction.creationDate">Creation Date</Translate>
            </span>
          </dt>
          <dd>
            {cartProductionEntity.creationDate ? (
              <TextFormat value={cartProductionEntity.creationDate} type="date" format={APP_LOCAL_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="quanlity">
              <Translate contentKey="tinhocdanangApp.cartProduction.quanlity">Quanlity</Translate>
            </span>
          </dt>
          <dd>{cartProductionEntity.quanlity}</dd>
          <dt>
            <Translate contentKey="tinhocdanangApp.cartProduction.cart">Cart</Translate>
          </dt>
          <dd>{cartProductionEntity.cart ? cartProductionEntity.cart.id : ''}</dd>
          <dt>
            <Translate contentKey="tinhocdanangApp.cartProduction.prodution">Prodution</Translate>
          </dt>
          <dd>{cartProductionEntity.prodution ? cartProductionEntity.prodution.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/cart-production" replace color="info">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/cart-production/${cartProductionEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ cartProduction }: IRootState) => ({
  cartProductionEntity: cartProduction.entity,
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(CartProductionDetail);
